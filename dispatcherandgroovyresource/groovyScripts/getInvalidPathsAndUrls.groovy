import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ResourceResolverFactory
import com.day.cq.wcm.api.Page
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.LoginException;
import org.apache.commons.lang3.StringUtils;
import javax.jcr.Session;
import javax.jcr.query.QueryManager
import javax.jcr.query.Query
import java.util.regex.*;

// THE PURPOSE OF THIS SCRIPT IS TO SEARCH PROPERTIES FOR A GIVEN SITE TO DETERMINE
// IF A PARTICULAR URL OR PATH IS BEING USED. THIS CAN BE BETTER THAN A PAGE REFERENCE
// CHECK SINCE SOMETIMES INTERNATIONAL SITES WILL BE POINTING TO THE INCORRECT PATH.

/* Starting path for the script to begin from */
def path = "/content/trex/cz/cs/academy"

skipUnpublishedPages = true
pathsToCheck = (String[])["/build-your-deck/planyourdeck/deck-designer","/products/accessory-hardware/hidden-fasteners-box"]
numberOfLinksFound = 0

// Inject the ResourceResolverFactory
def resourceResolverFactory = getService("org.apache.sling.api.resource.ResourceResolverFactory")

ResourceResolver resourceResolver = null
 
try {
    // Service user mapping (specify your service user ID)
    Map<String, Object> serviceUserMapping = new HashMap<>()
    serviceUserMapping.put(ResourceResolverFactory.SUBSERVICE, "trex-service-sites")
 
    // Obtain a ResourceResolver using a service user
    resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMapping)
    def rootPage = resourceResolver.getResource(path)
    println("PagePath|PropertyNodePath|PropertyName|PropertyValue")
    traverse(rootPage)
    println("Found " + numberOfLinksFound + " links matching your criteria.")
} catch (Exception e) {
    // Handle exceptions
    println("ERROR OCCURRED: "+e);
    /* e.printStackTrace() */
} finally {
    // Always close the resource resolver when done
    if (resourceResolver != null && resourceResolver.isLive()) {
        resourceResolver.close()
    }
}

def hasMatch(propertyValue) {
    return StringUtils.indexOfAny(propertyValue, pathsToCheck) != -1
}

def traverseNodeProperties(page,node) {
    def properties = node.properties
    while (properties.hasNext()) {
        def property = properties.next()
        def propertyPath = property.getPath()
        def propertyValue = ""

        if (property.isMultiple()) {
            def propertyValues = property.getValues() as String []
            propertyValue = propertyValues.join("##")
        } else {
            propertyValue = property.getValue().getString()
        }
        if(hasMatch(propertyValue)) {
            numberOfLinksFound = numberOfLinksFound + 1
            println(page.getPath() + "|" + propertyPath + "|"+"|"+propertyValue)
        }
    }
}

def traverse(respage) {
    if(respage != null) {
        Node node = respage.adaptTo(Node.class)
        if (node != null && node.hasNode("jcr:content")) {
            def jcrContentNode = node.getNode("jcr:content");
            def jcrContentNodePath = jcrContentNode.getPath();
            def isPublished = jcrContentNode.hasProperty("cq:lastReplicated")
            if(isPublished || !skipUnpublishedPages){ 
                QueryManager queryManager = session.getWorkspace().getQueryManager();
                String queryString = "SELECT * FROM [nt:base] WHERE ISDESCENDANTNODE('$jcrContentNodePath')";
                Query query = queryManager.createQuery(queryString, javax.jcr.query.Query.JCR_SQL2);
                NodeIterator iterator = query.execute().getNodes();
                def jcrNodeResult = traverseNodeProperties(respage,jcrContentNode);

                while (iterator.hasNext()) {
                    Node iterNode = iterator.nextNode();
                    def result = traverseNodeProperties(respage,iterNode);
                }
            }
        }

        Iterator<Resource> iter = respage.listChildren();
        while (iter.hasNext()) {
            traverse(iter.next());
        }
    }
}