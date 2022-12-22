package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.OSGiConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = OSGiConfig.class, immediate = true)
@Designate(ocd = OSGiConfigImpl.ServiceConfig.class)
public class OSGiConfigImpl implements OSGiConfig {
    private static final Logger log = LoggerFactory.getLogger(OSGiConfigImpl.class);

    @ObjectClassDefinition(name = "Munesh AEM Sample Config  - OSGi Configuration", description = "OSGi Munesh Configuration demo.")
    public @interface ServiceConfig {
        @AttributeDefinition(name = "Service Name", description = "Enter service name.", type = AttributeType.STRING)
        public String serviceName() default "AEM Geeks Service";

        @AttributeDefinition(name = "Service Count", description = "Add Service Count.", type = AttributeType.INTEGER)
        int getServiceCount() default 5;

        @AttributeDefinition(name = "Live Data", description = "Check this to get live data.", type = AttributeType.BOOLEAN)
        boolean getLiveData() default false;

        @AttributeDefinition(name = "Countries", description = "Add countries locales for which you want to run this service.", type = AttributeType.STRING)
        String[] getCountries() default { "de", "in" };

        @AttributeDefinition(name = "Run Modes", description = "Select Run Mode.", options = {
                @Option(label = "Author", value = "author"),
                @Option(label = "Publish", value = "publish"),
                @Option(label = "Both", value = "both")
        }, type = AttributeType.STRING)
        String getRunMode() default "both";

        @AttributeDefinition(name = "URL", description = "Add URL.", type = AttributeType.STRING)
        String getApiName() default "https";
    }

    private String serviceName;
    private int serviceCount;
    private boolean liveData;
    private String[] countries;
    private String runModes;
    private String apiName;

    @Activate
    protected void activate(ServiceConfig serviceConfig) {
        serviceName = serviceConfig.serviceName();
        log.info(serviceName);
        serviceCount = serviceConfig.getServiceCount();
        log.info("$serviceCount");
        liveData = serviceConfig.getLiveData();
        log.info("liveData");
        countries = serviceConfig.getCountries();
        log.info("countries");
        runModes = serviceConfig.getRunMode();
        log.info(runModes);
        apiName = serviceConfig.getApiName();
        log.info(apiName);
    }

    @Override
    public String getServiceName() {
        log.info("----------< Reading the config name >----------");
        return serviceName;
    }

    @Override
    public int getServiceCount() {
        log.info("----------< Reading the Count >----------");
        return serviceCount;
    }

    @Override
    public boolean isLiveData() {
        log.info("----------< Reading the data >----------");
        return liveData;
    }

    @Override
    public String[] getCountries() {
        return countries;
    }

    @Override
    public String getRunModes() {
        log.info("----------< Reading the runmode >----------");
        return runModes;
    }

    @Override
    public String getApiName() {
        return apiName;
    }
}