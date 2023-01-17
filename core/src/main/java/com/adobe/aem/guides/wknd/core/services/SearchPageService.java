package com.adobe.aem.guides.wknd.core.services;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;

import com.adobe.aem.guides.wknd.core.models.DTO.SearchPageDTO;

public interface SearchPageService {
    List<SearchPageDTO> getTitlePath(SlingHttpServletRequest request);

    List<SearchPageDTO> getWholePageList(SlingHttpServletRequest request);
}
