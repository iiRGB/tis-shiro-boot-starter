package org.tis.tools.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DubboProperties.DUBBO_PREFIX)
@ConditionalOnProperty(prefix = "abf.shiro", value = "enabled", havingValue = "true")
public class DubboProperties {

    public static final String DUBBO_PREFIX = "abf.shiro.dubbo";

    private ApplicationConfig application;

    private RegistryConfig registry;


    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public RegistryConfig getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryConfig registry) {
        this.registry = registry;
    }

}
