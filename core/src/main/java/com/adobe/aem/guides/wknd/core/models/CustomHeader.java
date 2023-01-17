package com.adobe.aem.guides.wknd.core.models;

import com.adobe.aem.guides.wknd.core.models.DTO.CustomHeaderDTO;

import java.util.List;

public interface CustomHeader extends CustomComponent {

    String getLogo();

    String getWeblink();

    String getSearch();

    String getHomeLink();

    List<CustomHeaderDTO> getCategory();
}
