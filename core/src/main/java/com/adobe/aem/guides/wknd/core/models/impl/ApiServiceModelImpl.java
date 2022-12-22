package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.models.ApiServiceModel;
import com.adobe.aem.guides.wknd.core.services.ApiServiceConfig;
import com.adobe.aem.guides.wknd.core.services.ConfigTest;
// import com.adobe.aem.guides.wknd.core.services.ApiServiceConfig;
import com.adobe.aem.guides.wknd.core.services.OSGiConfig;

@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { ApiServiceModel.class }, resourceType = {
        ApiServiceModelImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ApiServiceModelImpl implements ApiServiceModel {

    protected static final String RESOURCE_TYPE = "wknd/components/apiservicemodel";
    // private static final Logger LOG =
    // LoggerFactory.getLogger(ApiServiceConfig.class);

    // @OSGiService
    // ApiServiceConfig apiServiceConfig;

    @OSGiService
    ConfigTest configTest;

    @Override
    public String getApiName() {

        // String str = apiServiceConfig.getApiName();

        String str = configTest.getShahid();

        return str;
    }

}