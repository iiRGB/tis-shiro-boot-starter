package org.tis.tools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = ShiroProperties.SHIRO_PREFIX)
public class ShiroProperties {

    public static final String SHIRO_PREFIX = "abf.shiro";

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 匿名调用的接口
     */
    private String[] anons;

    /**
     * 资源权限
     */
    @NestedConfigurationProperty
    private PermissionProperties permission;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getAnons() {
        return anons;
    }

    public void setAnons(String[] anons) {
        this.anons = anons;
    }

    public PermissionProperties getPermission() {
        return permission;
    }

    public void setPermission(PermissionProperties permission) {
        this.permission = permission;
    }
}
