package org.tis.tools.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "abf.shiro", value = "enabled", havingValue = "true")
@EnableConfigurationProperties(DubboProperties.class)//开启属性注入,通过@autowired注入
@ConditionalOnClass({AnnotationBean.class, ApplicationConfig.class, RegistryConfig.class})
public class DubboConfig {

    @Autowired
    private DubboProperties prop;

//    @Bean
//    @ConditionalOnMissingBean(AnnotationBean.class)//容器中如果没有这个类,那么自动配置这个类
//    public static AnnotationBean annotationBean(@Value("${dubbo.annotation.package}")String packageName) {
//        AnnotationBean annotationBean = new AnnotationBean();
//        annotationBean.setPackage(packageName);
//        return annotationBean;
//    }

    @Bean
    @ConditionalOnMissingBean(ApplicationConfig.class)//容器中如果没有这个类,那么自动配置这个类
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(prop.getApplication().getName());
        applicationConfig.setOrganization(prop.getApplication().getOrganization());
        applicationConfig.setOwner(prop.getApplication().getOwner());
        applicationConfig.setLogger(prop.getApplication().getLogger());
        return applicationConfig;
    }

    @Bean
    @ConditionalOnMissingBean(RegistryConfig.class)//容器中如果没有这个类,那么自动配置这个类
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(prop.getRegistry().getAddress());
        registryConfig.setTimeout(prop.getRegistry().getTimeout());
        return registryConfig;
    }



}

