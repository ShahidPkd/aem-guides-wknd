package com.adobe.aem.guides.wknd.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;

import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component(service = { Servlet.class })
@SlingServletPaths("/services/trex/update-author-byline-for-articles")
@ServiceDescription("Servlet to allow us to update the Author ByLine for Articles. Sample csv data updated in the ticket WEB-4635, which can be taken as references")
public class UpdateAuthorByLineForArticlesServlet extends SlingSafeMethodsServlet {

    private final transient Logger logger = LoggerFactory.getLogger(UpdateAuthorByLineForArticlesServlet.class);

        // Instantiate a ResourceResolver.
        transient ResourceResolver resourceResolver = null;
        // will calculate total item succesfully updated
        private Integer totalSuccess;
        //property name which is going to add/update in node
        private static final String AUTHOR_PROPERTY_NAME = "allowedAuthors";

        //property name
        private static final String PUBLISH_PROPERTY_NAME = "cq:lastReplicationAction";

        @Reference
        private transient Replicator replicator;
  

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException  {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        resourceResolver = request.getResourceResolver();

        // Get CSV Asset parameters from URL.
        String csvPath = request.getParameter("csvUrl");
        Session session = resourceResolver.adaptTo(Session.class);

        HashMap<String, ArrayList<String>> data = getCsvData(csvPath);

        List <String> keyName = new ArrayList<>();

        for (String key : data.keySet()) {
            keyName.add(key);
        }
        
        Integer i = 0;
        totalSuccess = 0;
        while(data.get(keyName.get(0)).size() > i && data.get(keyName.get(1)) != null){
            mapAuthorToArticle(session, data.get(keyName.get(0)).get(i), data.get(keyName.get(1)).get(i), response, i);
            i++;
        }
        response.getWriter().write("Total Data: " + data.get(keyName.get(0)).size() + "\n");
        response.getWriter().write("Successfully Data Updated: " + totalSuccess + "\n");
        Integer failed = data.get(keyName.get(0)).size() - totalSuccess;
        if(failed != 0){
            response.getWriter().write("Failed Data: " + failed + ", please fixed the reason and update csv file...\n");
        } 
    }

    private void mapAuthorToArticle(Session session, String articlePath, String authorPath, SlingHttpServletResponse response, Integer i) throws IOException {
        Resource articleResource = resourceResolver.getResource(articlePath.charAt(articlePath.length()-1) == '/' ? articlePath + JcrConstants.JCR_CONTENT : articlePath + "/" + JcrConstants.JCR_CONTENT);
        Resource authorResource = resourceResolver.getResource(authorPath.charAt(authorPath.length()-1) == '/' ? authorPath + JcrConstants.JCR_CONTENT : authorPath + "/" + JcrConstants.JCR_CONTENT);
        if(articleResource != null && authorResource != null){
            ModifiableValueMap map = articleResource.adaptTo(ModifiableValueMap.class);
            ModifiableValueMap authorPublish = authorResource.adaptTo(ModifiableValueMap.class);
            if(authorPublish.get(PUBLISH_PROPERTY_NAME, "").equalsIgnoreCase("Activate")){
                map.put(AUTHOR_PROPERTY_NAME, authorPath);
                resourceResolver.commit();
                try {
                    replicator.replicate(session, ReplicationActionType.ACTIVATE, articlePath, null);
                    response.getWriter().write(++i + " Successfully Updated and Published  ---Article Path: " + articlePath + " Author: " + authorPath +"\n");
                    totalSuccess +=1; 
                } catch (ReplicationException e) {
                    response.getWriter().write(++i + " ERROR: Successfully Updated But Not able to publish  ---Article Path: " + authorPublish.get("cq:lastReplicationAction") + " may be you dont have permission and error:" + e + "\n");
                }
            }else{
                response.getWriter().write(++i + " ERROR: Author Page Found But Not Published, Please Publish author page first ---Author Path: " + authorPath + "\n");
            }
        }else if(articleResource == null){
            response.getWriter().write(++i + " ERROR: Article Page Not Found ---Article Path: " + articlePath + "\n");
        }else{
            response.getWriter().write(++i + " ERROR: Author Page Not Found  ---Author Path: " + authorPath + "\n");
        }
        
    }

    // method to get keys and values from CSV (keys will store the header row of the CSV (the column names), value = all rows data).
    private HashMap<String, ArrayList<String>> getCsvData(String csvPath) {

        HashMap<String, ArrayList<String>> csvHashMap = new HashMap<>();
        try {
            Resource resource = resourceResolver.getResource(csvPath);
            Asset asset = resource.adaptTo(Asset.class);
        
            BufferedReader br = new BufferedReader(new InputStreamReader(asset.getOriginal().getStream()));
            String line;
            ArrayList<String> keys = null;
        
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split(",");
        
                if (i == 0) {
                    keys = new ArrayList<>(Arrays.asList(rowValues));
                } else {
                    for (int j = 0; j < rowValues.length && j < keys.size(); j++) {
                        String key = keys.get(j);
                        String value = rowValues[j];
        
                        csvHashMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
                    }
                }
                i++;
            }
        } catch (Exception e) {
            logger.info("CSV file have not appropriate data");
        }
        return csvHashMap;
    }
}