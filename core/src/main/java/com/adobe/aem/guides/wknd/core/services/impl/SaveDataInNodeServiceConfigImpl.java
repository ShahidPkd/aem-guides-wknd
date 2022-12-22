package com.adobe.aem.guides.wknd.core.services.impl;

// import com.adobe.aem.guides.wknd.core.services.OSGiConfig;
import com.adobe.aem.guides.wknd.core.services.SaveDataInNodeServiceConfig;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = SaveDataInNodeServiceConfig.class, immediate = true)
@Designate(ocd = SaveDataInNodeServiceConfigImpl.ServiceConfig.class)
public class SaveDataInNodeServiceConfigImpl implements SaveDataInNodeServiceConfig {
    private static final Logger log = LoggerFactory.getLogger(SaveDataInNodeServiceConfigImpl.class);

    @ObjectClassDefinition(name = "Save Data In Node Sample Config  - OSGi Configuration", description = "OSGi Descriptionfor the sample Shahid Configuration demo.")
    public @interface ServiceConfig {

        @AttributeDefinition(

                name = "Resource Path", description = "Enter Resource Path.", type = AttributeType.STRING)
        public String serviceName() default "/apps/wknd/components/myblogs";

        @AttributeDefinition(

                name = "Node Name", description = "Enter Node name.", type = AttributeType.STRING)
        public String apiName() default "mydetails";

        // @AttributeDefinition(

        // name = "Path Name", description = "Enter Node name.", type =
        // AttributeType.STRING)
        // public String resourcePath() default "/apps/wknd/components/myblogs";

        // @AttributeDefinition(

        // name = "Node Name", description = "Enter Node name.", type =
        // AttributeType.STRING)
        // public String nodeName() default "mynewlist";

    }

    private String serviceName;

    private String apiName;

    // private String resourcePath;

    // private String nodeName;

    @Activate
    protected void activate(ServiceConfig serviceConfig) {
        serviceName = serviceConfig.serviceName();
        log.info(serviceName);
        apiName = serviceConfig.apiName();
        log.info(apiName);

        // resourcePath = serviceConfig.resourcePath();
        // log.info(resourcePath);

        // nodeName = serviceConfig.nodeName();
        // log.info(nodeName);
    }

    @Override
    public String getRootPath() {
        return serviceName;
    }

    @Override
    public String getUserNode() {

        return apiName;
    }

}