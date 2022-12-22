package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.Iterator;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.models.ModelDemoService;

import com.adobe.aem.guides.wknd.core.services.DemoService;
import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class, adapters = ModelDemoService.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ModelDemoServiceImpl implements ModelDemoService {
    private static final Logger LOG = LoggerFactory.getLogger(ModelDemoService.class);

    @OSGiService
    DemoService demoService;

    @Override
    public Iterator<Page> getPageList() {
        return demoService.getPages();
    }
}