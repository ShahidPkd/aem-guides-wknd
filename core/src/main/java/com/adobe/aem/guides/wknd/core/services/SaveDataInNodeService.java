package com.adobe.aem.guides.wknd.core.services;

import org.apache.sling.api.SlingHttpServletRequest;

public interface SaveDataInNodeService {

    String saveDataInNode(SlingHttpServletRequest request) throws Exception;

}
