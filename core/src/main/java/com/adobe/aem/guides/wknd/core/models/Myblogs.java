package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

import com.adobe.aem.guides.wknd.core.models.DTO.BlogsList;

public interface Myblogs {
    String getResourceR();

    List<BlogsList> getMyBlogList();
}
