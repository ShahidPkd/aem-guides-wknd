package com.adobe.aem.guides.wknd.core.services.impl;

import org.osgi.service.component.annotations.Component;

import com.adobe.aem.guides.wknd.core.services.OSGIService;

@Component
public class OSGIServiceImpl implements OSGIService {

    @Override
    public String getData() {
        return "This is an OSGI service method";
    }
    
}
