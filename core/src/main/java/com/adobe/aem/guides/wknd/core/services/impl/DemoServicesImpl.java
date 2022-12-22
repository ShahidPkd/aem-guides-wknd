package com.adobe.aem.guides.wknd.core.services.impl;

import java.util.Iterator;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.aem.guides.wknd.core.services.DemoService;
import com.adobe.aem.guides.wknd.core.utils.ResolverUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(service = DemoService.class, immediate = true)
public class DemoServicesImpl implements DemoService {
    private static final Logger LOG = LoggerFactory.getLogger(DemoServicesImpl.class);
    @Reference
    private ResourceResolverFactory resourceResolverFactory;

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

    @Override
    public Iterator<Page> getPages() {
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Page page = pageManager.getPage("/content/wknd/us/en");
            Iterator<Page> pages = page.listChildren();
            return pages;
        } catch (LoginException e) {
            LOG.info("\n Login Exception {} ", e.getMessage());
        }
        return null;
    }
}