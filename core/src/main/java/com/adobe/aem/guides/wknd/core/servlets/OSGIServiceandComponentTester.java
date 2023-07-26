package com.adobe.aem.guides.wknd.core.servlets;
// servlet neccessory import step by step

import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.services.OSGIComponent;
import com.adobe.aem.guides.wknd.core.services.OSGIService;
import com.adobe.aem.guides.wknd.core.services.impl.OSGIComponentImpl;

import javax.servlet.ServletException;
import java.io.IOException;



@Component(service = Servlet.class)
@SlingServletPaths(value = {"/bin/osgi"})
public class OSGIServiceandComponentTester extends SlingAllMethodsServlet {

    // @Reference
    // OSGIService osgiService;

    @Reference
    OSGIComponent osgiComponent;



    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {
        
                resp.getWriter().write(osgiComponent.getDataC());
                // resp.getWriter().write(osgiService.getData());


    }

}
