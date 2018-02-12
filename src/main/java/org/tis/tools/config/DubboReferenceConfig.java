package org.tis.tools.config;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tis.tools.rservice.ac.capable.IAuthenticationRService;
import org.tis.tools.rservice.ac.capable.IOperatorRService;
import org.tis.tools.rservice.ac.capable.IRoleRService;

@Configuration
@ConditionalOnProperty(prefix = "abf.shiro", value = "enabled", havingValue = "true")
public class DubboReferenceConfig {

    private static final String GROUP_AC = "ac";


    @Bean
    public ReferenceBean<IAuthenticationRService> authenticationRService() {
        ReferenceBean<IAuthenticationRService> ref = new ReferenceBean<>();
        ref.setInterface(IAuthenticationRService.class);
        ref.setGroup(GROUP_AC);
        ref.setVersion("0.9");
        ref.setCheck(false);
        return ref;
    }


    @Bean
    public ReferenceBean<IOperatorRService> operatorRService() {
        ReferenceBean<IOperatorRService> ref = new ReferenceBean<>();
        ref.setInterface(IOperatorRService.class);
        ref.setGroup(GROUP_AC);
        ref.setVersion("0.9");
        ref.setCheck(false);
        return ref;
    }

    @Bean
    public ReferenceBean<IRoleRService> roleRService() {
        ReferenceBean<IRoleRService> ref = new ReferenceBean<>();
        ref.setInterface(IRoleRService.class);
        ref.setGroup(GROUP_AC);
        ref.setVersion("0.9");
        ref.setCheck(false);
        return ref;
    }

}
