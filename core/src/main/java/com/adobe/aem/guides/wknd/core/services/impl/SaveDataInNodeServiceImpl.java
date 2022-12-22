package com.adobe.aem.guides.wknd.core.services.impl;

import javax.jcr.Node;

import javax.jcr.Session;
import javax.servlet.annotation.MultipartConfig;

import org.apache.sling.api.SlingHttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.core.services.SaveDataInNodeServiceConfig;
import com.adobe.aem.guides.wknd.core.services.SaveDataInNodeService;
import com.adobe.aem.guides.wknd.core.utils.ResolverUtil;

// logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = SaveDataInNodeService.class, immediate = true)

public class SaveDataInNodeServiceImpl implements SaveDataInNodeService {
    private static final Logger LOG = LoggerFactory.getLogger(DemoServicesImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    SaveDataInNodeServiceConfig saveDataIndNodeServiceConfig;

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

    String responseString = "null";

    @Override
    public String saveDataInNode(SlingHttpServletRequest request) throws Exception {

        ResourceResolver resourceResolver;
        try {
            resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            // resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);

            Resource resource1 = resourceResolver.getResource(saveDataIndNodeServiceConfig.getRootPath().toString());

            String nodeName = saveDataIndNodeServiceConfig.getUserNode();

            Node nodetemp = resource1.adaptTo(Node.class);

            if (nodetemp.hasNode(nodeName)) {
                resource1 = resourceResolver.getResource(saveDataIndNodeServiceConfig.getRootPath() + "/" + nodeName);
            } else {
                nodetemp.addNode(nodeName, "nt:unstructured");
                resource1 = resourceResolver.getResource(saveDataIndNodeServiceConfig.getRootPath() + "/" + nodeName);
            }

            Node node1 = resource1.adaptTo(Node.class);
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");

            if (node1.hasNode(fname + "_" + lname)) {
                responseString = "Already Created, Dublicate Data";
            } else {
                Node node2 = node1.addNode(fname + "_" + lname);
                // node1.addNode(fname, "nt:unstructured");

                // node2.equals(true);
                node2.setProperty("fname", fname);
                node2.setProperty("lname", lname);
                responseString = "Node 1 added Successfully....";
            }
            session.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }
}
