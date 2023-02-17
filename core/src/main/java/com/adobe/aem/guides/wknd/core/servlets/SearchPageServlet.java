package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.models.DTO.SearchPageDTO;
import com.adobe.aem.guides.wknd.core.services.SearchPageService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

// list to json
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/search" })

public class SearchPageServlet extends SlingAllMethodsServlet {
    // private static final Logger LOG =
    // LoggerFactory.getLogger(MyPathTypeServlet.class);

    @Reference
    SearchPageService searchPageService;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {

        List<SearchPageDTO> results = searchPageService.getTitlePath(req);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String fResultsJson = ow.writeValueAsString(results);

        resp.setContentType("application/json");
        resp.getWriter().write(fResultsJson);

    }

}