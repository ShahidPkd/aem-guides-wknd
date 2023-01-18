package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;

import com.adobe.aem.guides.wknd.core.models.CustomHeader;
import com.adobe.aem.guides.wknd.core.models.CustomSearch;
import com.adobe.aem.guides.wknd.core.models.DTO.CustomHeaderDTO;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { CustomSearch.class }, resourceType = {
        CustomHeaderImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class CustomSearchImpl extends CustomComponentImpl implements CustomSearch {

    protected static final String RESOURCE_TYPE = "wknd/components/customsearch";

    @ValueMapValue
    String pathLink;

    @ValueMapValue
    private List<CustomHeaderDTO> category;

    // this method will return boolean for for setting component Name if data is
    // empty
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(pathLink);
    }

    @Override
    public String getPathLink() {

        return pathLink;
    }
}