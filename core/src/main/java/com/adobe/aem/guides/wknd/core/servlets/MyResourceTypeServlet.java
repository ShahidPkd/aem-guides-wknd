package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.services.DemoService;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
// import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

// import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
// import java.util.List;
import java.util.Iterator;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = "wknd/components/page", selectors = "myservlet", extensions = "xml", methods = HttpConstants.METHOD_GET)
public class MyResourceTypeServlet extends SlingAllMethodsServlet {
    // private static final Logger LOG =
    // LoggerFactory.getLogger(MyResourceTypeServlet.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.
     * api.SlingHttpServletRequest, org.apache.sling.api.SlingHttpServletResponse)
     */

    @Reference
    DemoService demoService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {

        final Resource resource = req.getResource();
        resp.setContentType("text/plain");

        resp.getWriter().write("Page Title name is = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
        // LOG.info("==PARAMETERS===> ");
    }

}