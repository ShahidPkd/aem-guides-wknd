package com.adobe.aem.guides.wknd.core.services.impl;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;

// for resource resolver
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.services.SearchPageService;
import com.adobe.aem.guides.wknd.core.models.DTO.SearchPageDTO;

// for query builder all classes
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.Query;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.result.SearchResult;
import com.day.cq.search.result.Hit;
import org.apache.sling.api.resource.Resource;
import com.day.cq.wcm.api.Page;

// logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = SearchPageService.class, immediate = true)

public class SearchPageServiceImpl implements SearchPageService {
    private static final Logger LOG = LoggerFactory.getLogger(SearchPageServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    QueryBuilder queryBuilder;
    Session session;

    @Activate
    public void activate(ComponentContext componentContext) {
        LOG.info("\n ==============INSIDE ACTIVATE shahid================");
        LOG.info("\n {} = {} ", componentContext.getBundleContext().getBundle().getBundleId(),
                componentContext.getBundleContext().getBundle().getSymbolicName());
    }

    @Deactivate
    public void deactivate(ComponentContext componentContext) {
        LOG.info("\n ==============INSIDE DEACTIVATE shahid================");
    }

    @Modified
    public void modified(ComponentContext componentContext) {
        LOG.info("\n ==============INSIDE MODIFIED shahid================");
    }

    String responseString = "null";

    @Override
    public List<SearchPageDTO> getTitlePath(SlingHttpServletRequest request) {
        // this will call when we call ajax and this will shown in the drop-down with
        // page title and image

        ResourceResolver resourceResolver = request.getResourceResolver();
        session = resourceResolver.adaptTo(Session.class);

        String SPATH = "/content/experience-fragments/wknd/us/en/site/header/master/jcr:content/root/customsearch";
        ResourceResolver resourceResolver2 = request.getResourceResolver();

        Resource resource2 = resourceResolver2.getResource(SPATH);
        ValueMap property = resource2.adaptTo(ValueMap.class);
        String searchPath = property.get("pathLink", String.class);

        String pageNumber = request.getParameter("page");
        int pageNo = Integer.parseInt(pageNumber);
        int resultPerPage = 10;
        int offsetValue = (pageNo == 1 ? 0 : (resultPerPage * (pageNo - 1)) + 1);

        Map<String, String> predicates = new HashMap<>();
        predicates.put("path", searchPath);
        predicates.put("type", "cq:Page");
        predicates.put("1_property", "jcr:content/jcr:title");
        predicates.put("1_property.value", request.getParameter("query") + "%");
        predicates.put("1_property.operation", "like");
        predicates.put("p.offset", String.valueOf(offsetValue));
        predicates.put("p.limit", String.valueOf(pageNo * 10));

        Query query = null;
        try {
            query = queryBuilder.createQuery(PredicateGroup.create(predicates), session);
        } catch (Exception e) {
            // LOGGER.error("Error in Query Check kr le bhai try block me Shahid bhai bol
            // rha");
        }

        SearchResult searchResult = query.getResult();

        List<SearchPageDTO> results = new ArrayList<>();
        for (Hit hit : searchResult.getHits()) {
            String path = null;

            SearchPageDTO data = new SearchPageDTO();

            try {
                path = hit.getPath();
                Resource articlResource = resourceResolver.getResource(path);
                Page articlPage = articlResource.adaptTo(Page.class);

                data.setTitle(articlPage.getTitle());
                data.setPath(path + ".html");
                results.add(data);

            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
        return results;
    }

    @Override
    public List<SearchPageDTO> getWholePageList(SlingHttpServletRequest request) {
        // this with return title, description, path and image of the page
        return null;
    }
}
