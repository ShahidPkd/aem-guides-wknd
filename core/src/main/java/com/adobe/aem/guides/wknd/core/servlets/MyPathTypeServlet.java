package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.servlets.annotations.SlingServletPaths;

import org.osgi.service.component.annotations.Component;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.services.DemoService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;
import com.day.cq.wcm.api.Page;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/pages", "/geeks/pages" })
public class MyPathTypeServlet extends SlingAllMethodsServlet {
    // private static final Logger LOG =
    // LoggerFactory.getLogger(MyPathTypeServlet.class);

    @Reference
    DemoService demoService;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {
        Iterator<Page> pages = demoService.getPages();

        String id = req.getParameter("id");

        if (id != null) {
            resp.getWriter().write(id);
        } else {
            resp.getWriter().write("Invalid Parameter! Please pass parameter");
        }

        // while (pages.hasNext()) {
        // Page childPage = pages.next();
        // resp.getWriter().write(childPage.getName() + "\n");

        // }

        // resp.getWriter().write("path type servlet");
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");

        if (name != null) {
            response.getWriter().write(name);
        } else {
            response.getWriter().write("Invalid Parameter! Please pass parameter");
        }

    }
}