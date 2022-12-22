package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.servlets.annotations.SlingServletPaths;

import org.osgi.service.component.annotations.Component;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.services.SaveDataInNodeService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/savedatainnode" })
public class SaveDataInNodeServlet extends SlingAllMethodsServlet {
    // private static final Logger LOG =
    // LoggerFactory.getLogger(MyPathTypeServlet.class);
    @Reference
    SaveDataInNodeService saveDataInNodeService;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {

        String getStatus;
        try {
            getStatus = saveDataInNodeService.saveDataInNode(req);
            resp.getWriter().write(getStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    }
}