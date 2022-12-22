package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.Myblogs;
import com.adobe.aem.guides.wknd.core.models.DTO.BlogsList;
import com.adobe.granite.haf.annotations.ApiQuery;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;

// these three packages are responsible for model 
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

//mapping dialog value 
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

//resolver
import org.apache.sling.models.annotations.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// for post construct for init
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

// resource and resource resolver
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

// for logger 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { Myblogs.class }, resourceType = {
        MyblogsImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class MyblogsImpl implements Myblogs {

    protected static final String RESOURCE_TYPE = "wknd/components/myblogs";

    private static final Logger LOGGER = LoggerFactory.getLogger(MyblogsImpl.class);

    // @Self
    // Resource resource;

    QueryBuilder queryBuilder;
    Session session;

    @Inject
    @Source("sling-object")
    private ResourceResolver resourceResolver;

    @ValueMapValue
    String mainpath;

    List<BlogsList> myPageList = new ArrayList<>();

    public MyblogsImpl() {
    }

    @PostConstruct
    protected void init() {

        // ResourceResolver resolver = resource.getResourceResolver();

        // here we need to adapt the particular class
        session = resourceResolver.adaptTo(Session.class);
        queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);

        // predicates which is refer to our query
        Map<String, String> predicate = new HashMap<>();
        predicate.put("path", mainpath);
        predicate.put("type", "cq:Page");

        Query query = null;

        try {
            query = queryBuilder.createQuery(PredicateGroup.create(predicate), session);

        } catch (Exception e) {
            LOGGER.error("Error in Query Check kr le bhai try block me Shahid bhai bol rha");
        }

        SearchResult searchResult = query.getResult();

        for (Hit hit : searchResult.getHits()) {
            String path = null;

            try {
                BlogsList temp = new BlogsList();
                path = hit.getPath();
                Resource articlResource = resourceResolver.getResource(path);
                Page articlPage = articlResource.adaptTo(Page.class);
                String title = articlPage.getTitle();

                String description = articlPage.getDescription();

                temp.setPagePath(path);
                temp.setPathTitle(title);
                temp.setPathDescription(description);
                myPageList.add(temp);

                // LOGGER.debug("" + description + " " + title);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }

        }

    }

    // testing purpose not used because it
    @Override
    public String getResourceR() {

        String str = "Before resolver closed " + resourceResolver.isLive() + "\n";
        resourceResolver.close();
        String str2 = "After closing of Resolver " + resourceResolver.isLive() + " Did you understand ?";
        String str3 = str + str2;

        return str3;
    }

    @Override
    public List<BlogsList> getMyBlogList() {

        return myPageList;
    }

}
