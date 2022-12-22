package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.ApiServiceConfig;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ApiServiceConfig.class, immediate = true)
@Designate(ocd = ApiServiceConfigImpl.ServiceConfig.class)
public class ApiServiceConfigImpl implements ApiServiceConfig {
    private static final Logger log = LoggerFactory.getLogger(ApiServiceConfigImpl.class);

    @ObjectClassDefinition(name = "Shahid Api Sample Config  - OSGi Configuration", description = "OSGi Descriptionfor the sample Shahid Configuration demo.")
    public @interface ServiceConfig {

        @AttributeDefinition(

                name = "Service Name", description = "Enter service name.", type = AttributeType.STRING)
        public String serviceName() default "/apps/wknd/components/myblogs";

        @AttributeDefinition(

                name = "Api Url", description = "Enter Api URL.", type = AttributeType.STRING)
        public String apiName() default "mydetails";

    }

    private String serviceName;

    private String apiName;

    @Activate
    protected void activate(ServiceConfig serviceConfig) {
        serviceName = serviceConfig.serviceName();
        log.info(" ok " + serviceName);
        apiName = serviceConfig.apiName();
        log.info(apiName);
    }

    @Override
    public String getApiName() {
        return apiName;
    }

    @Override
    public String getServiceName() {

        return serviceName;
    }
}
