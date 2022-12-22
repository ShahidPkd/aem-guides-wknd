package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import java.util.HashMap;
import java.util.Map;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/pagesloader" })
public class MyPageLoader extends SlingAllMethodsServlet {
    @Reference
    QueryBuilder queryBuilder;
    Session session;
    JsonArray jsonArray = new JsonArray();
    JsonObject jsonObject = new JsonObject();

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("apikey");
        if (id != null) {
            if (id.equals("shahidaem")) {
                ResourceResolver resourceResolver = req.getResourceResolver();
                session = resourceResolver.adaptTo(Session.class);
                // predicates which is refer to our query
                Map<String, String> predicate = new HashMap<>();
                predicate.put("path", "/content/wknd/us/en/blogs");
                predicate.put("type", "cq:Page");
                Query query = null;
                try {
                    query = queryBuilder.createQuery(PredicateGroup.create(predicate), session);
                } catch (Exception e) {
                    // LOGGER.error("Error in Query Check kr le bhai try block me Shahid bhai bol
                    // rha");
                }
                JsonObject tempObj = new JsonObject();
                SearchResult searchResult = query.getResult();
                for (Hit hit : searchResult.getHits()) {
                    String path = null;

                    try {
                        path = hit.getPath();
                        Resource articlResource = resourceResolver.getResource(path);
                        Page articlPage = articlResource.adaptTo(Page.class);
                        String title = articlPage.getTitle();
                        String description = articlPage.getDescription();
                        tempObj.addProperty("title", title);
                        tempObj.addProperty("description", description);
                        tempObj.addProperty("link", path + ".html");
                        jsonArray.add(tempObj);
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                }
                jsonObject.add("data", jsonArray);
            } else {
                jsonObject.addProperty("data", "Invalid parameter! please ask to admin for apikey value=" + id);
            }
        } else {
            jsonObject.addProperty("data",
                    "Invalid URL and Key & Parameter! Please Check ");
        }
        resp.setContentType("application/json");
        resp.getWriter().print(jsonObject);
    }
}