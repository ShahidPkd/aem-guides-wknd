import org.apache.sling.api.resource.ResourceResolver
import org.apache.sling.api.resource.ResourceResolverFactory
import com.day.cq.wcm.api.Page
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.LoginException;
import javax.jcr.Session;
import javax.jcr.query.QueryManager
import javax.jcr.query.Query
import java.util.regex.*;

// THE PURPOSE OF THIS SCRIPT IS PROVIDE A REPORT OF PUBLISHED URLs FOR A SITE

/* Starting path for the script to begin from */
def path = "/content/trex/de/de/"

// Inject the ResourceResolverFactory
def resourceResolverFactory = getService("org.apache.sling.api.resource.ResourceResolverFactory")

ResourceResolver resourceResolver = null
 
try {
    // Service user mapping (specify your service user ID)
    Map<String, Object> serviceUserMapping = new HashMap<>()
    serviceUserMapping.put(ResourceResolverFactory.SUBSERVICE, "trex-service-sites")

    println("Path,Published?");
 
    // Obtain a ResourceResolver using a service user
    resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMapping)
    def rootPage = resourceResolver.getResource(path)
    traverse(rootPage)
} catch (Exception e) {
    // Handle exceptions
    println("ERROR OCCURRED: "+e);
} finally {
    // Always close the resource resolver when done
    if (resourceResolver != null && resourceResolver.isLive()) {
        resourceResolver.close()
    }
}

def traverse(respage) {
    if(respage != null) {
        Node node = respage.adaptTo(Node.class)
        if (node != null && node.hasNode("jcr:content")) {
            def jcrContentNode = node.getNode("jcr:content");
            def isPublished = jcrContentNode.hasProperty("cq:lastReplicated")
            println(node.getPath() + "," + isPublished)
        }

        Iterator<Resource> iter = respage.listChildren();
        while (iter.hasNext()) {
            traverse(iter.next());
        }
    }
}
