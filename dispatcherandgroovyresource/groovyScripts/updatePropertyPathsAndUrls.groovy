import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ResourceResolverFactory
import com.day.cq.wcm.api.Page
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.LoginException;
import javax.jcr.Session;
import javax.jcr.query.QueryManager
import javax.jcr.query.Query
import java.util.regex.*;

// THE PURPOSE OF THIS SCRIPT IS TO FIX LINK ISSUES ON LANGUAGE/LIVE COPY SITES WHERE
// RICH TEXT FIELDS ARE POINTING TO THE WRONG LANGUAGE MASTER.

// If running directly on EN for path-based links, these should be the same.
// This script will replace the sourceBase with the targetBase during href updates.
sourceBase = "/content/dam/trex/it/content-fragments/language-masters/en/"
targetBase = "/content/dam/trex/it/content-fragments/language-masters/cs/"

/* Starting path for the script to begin from */
def path = "/content/trex/cz/cs/academy"

/* Only append HTML if sourceBase is an AEM path and not a domain */
shouldAppendHtml = sourceBase.startsWith("/content/trex")

// Inject the ResourceResolverFactory
def resourceResolverFactory = getService("org.apache.sling.api.resource.ResourceResolverFactory")

numberOfLinksUpdated = 0

ResourceResolver resourceResolver = null
 
try {
    // Service user mapping (specify your service user ID)
    Map<String, Object> serviceUserMapping = new HashMap<>()
    serviceUserMapping.put(ResourceResolverFactory.SUBSERVICE, "trex-service-sites")
 
    // Obtain a ResourceResolver using a service user
    resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMapping)
    def rootPage = resourceResolver.getResource(path)
    traverseAndUpdate(rootPage)
    resourceResolver.commit();
    save();
    println("DONE! Updated " + numberOfLinksUpdated + " links.");
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


def appendHtmlToHref(htmlString) {
        String regex = "<a\\s+([^>]*)href\\s*=\\s*\"(" + Pattern.quote(sourceBase) + "[^\"]*)\"([^>]*)>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlString);

        StringBuffer modifiedHtml = new StringBuffer();

        while (matcher.find()) {
            String attributesBeforeHref = matcher.group(1);
            String hrefValue = matcher.group(2);
            String hrefBefore = hrefValue;

            hrefValue = hrefValue.replaceAll(sourceBase, targetBase);
             
            String attributesAfterHref = matcher.group(3);
            if (shouldAppendHtml && !hrefValue.contains(".html")) {
                hrefValue += ".html";
            }
            matcher.appendReplacement(modifiedHtml, "<a " + attributesBeforeHref + "href=\"" + hrefValue + "\"" + attributesAfterHref + ">");
        }

        matcher.appendTail(modifiedHtml);
        return modifiedHtml.toString();
}



def traverseNodeProperties(node) {
    def properties = node.properties
    def hasUpdates = false
    while (properties.hasNext()) {
        def property = properties.next()
        def propertyName = property.name
        /* println("Property name: "+propertyName) */
        /* println("Property is multi-valued: "+ property.isMultiple()) */
        /* Skipping for the OOTB properties on nodes*/
        if (propertyName.startsWith("jcr:") || (propertyName.startsWith("cq:") && !propertyName.startsWith("cq:redirectTarget"))) {
            continue;
        }
        if(property.isMultiple()) {
            def propertyValues = property.getValues() as String []
            def updatedPropertyValues = property.getValues() as String []
            def updatedMultiValue = false
            for(int i=0; i < updatedPropertyValues.length; i++) {
                def propertyValue = updatedPropertyValues[i]
                def updatedPropertyValue = propertyValue.replaceAll(sourceBase, targetBase);
                if(propertyValue != updatedPropertyValue) {
                    updatedPropertyValues[i] = updatedPropertyValue
                    hasUpdates = true
                    updatedMultiValue = true
                    println("Node path: " + node.getPath())
                    println("Updated property value from: " + propertyValue + " to: "+ updatedPropertyValue)
                }
            }
            if(updatedMultiValue) {
                numberOfLinksUpdated = numberOfLinksUpdated + 1
                node.setProperty(propertyName,updatedPropertyValues)
            }
        } else {
            def propertyValue = property.getValue().getString()
            /* println("Property value: "+propertyValue) */

            def updatedPropertyValue = appendHtmlToHref(propertyValue)
            updatedPropertyValue = updatedPropertyValue.replaceAll(sourceBase, targetBase);
                
            if(propertyValue != updatedPropertyValue){
                println("Property Value Before: " + propertyValue);
                println("Property Value After: " + updatedPropertyValue);
            }
            /* println("Updated property value: "+updatedPropertyValue) */
            
            if(propertyValue != updatedPropertyValue) {
                println("Node path: "+node.getPath())
                numberOfLinksUpdated = numberOfLinksUpdated + 1
                println("Updated property value from: " + propertyValue + " to: "+ updatedPropertyValue)
                node.setProperty(propertyName,updatedPropertyValue)
                hasUpdates = true
            }
        }
    }

    return hasUpdates
}

def traverseAndUpdate(respage) {
    if(respage != null) {
        /* println("Page Path: "+ respage.getPath()) */
        Node node = respage.adaptTo(Node.class)
        if (node != null && node.hasNode("jcr:content")) {
            def jcrContentNode = node.getNode("jcr:content");
            def isPublished = jcrContentNode.hasProperty("cq:lastReplicated")
            /* println("Page is published: "+ isPublished) */
            def jcrContentNodePath = jcrContentNode.getPath();
            QueryManager queryManager = session.getWorkspace().getQueryManager();
            String queryString = "SELECT * FROM [nt:base] WHERE ISDESCENDANTNODE('$jcrContentNodePath')";
            Query query = queryManager.createQuery(queryString, javax.jcr.query.Query.JCR_SQL2);
            NodeIterator iterator = query.execute().getNodes();
            def hasUpdates = false
            def jcrNodeResult = traverseNodeProperties(jcrContentNode);

            if(jcrNodeResult) {
                hasUpdates = true;
            }

            while (iterator.hasNext()) {
                Node iterNode = iterator.nextNode();
                def result = traverseNodeProperties(iterNode);
                if(result) {
                    hasUpdates = true
                }
            }
            
            if (isPublished && hasUpdates) {
                activate(node.getPath())
            }
        }

        Iterator<Resource> iter = respage.listChildren();
        while (iter.hasNext()) {
            traverseAndUpdate(iter.next());
        }
    }
}

def save() {
    session.save()
    session.refresh(true)
}
