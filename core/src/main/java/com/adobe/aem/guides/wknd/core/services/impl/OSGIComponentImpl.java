package com.adobe.aem.guides.wknd.core.services.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.services.OSGIComponent;
import com.adobe.aem.guides.wknd.core.services.OSGIService;


@Component
public class OSGIComponentImpl implements OSGIComponent {

    @Reference
    OSGIService osgiService;
    @Override
    public String getDataC() {
        return osgiService.getData() + " This is the returning from OSGI Component";
    }
    
}
