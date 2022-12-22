package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.ConfigTest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ConfigTest.class, immediate = true)
@Designate(ocd = ConfigTestImpl.ServiceConfig.class)
public class ConfigTestImpl implements ConfigTest {
    private static final Logger log = LoggerFactory.getLogger(ConfigTestImpl.class);

    @ObjectClassDefinition(name = "Registering  - OSGi Configuration", description = "OSGi Descriptionfor the sample Shahid Configuration demo.")
    public @interface ServiceConfig {

        @AttributeDefinition(

                name = "Your Name", description = "Enter name.", type = AttributeType.STRING)
        public String myName() default "Shahid";

    }

    private String yName;

    @Activate
    protected void activate(ServiceConfig serviceConfig) {
        yName = serviceConfig.myName();
        log.info(yName);
    }

    @Override
    public String getShahid() {
        return yName;
    }
}
