package com.company.ProjectSpring.jmx;

import com.company.ProjectSpring.service.ConfigService;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@ManagedResource(description = "Application config bean")
public class ConfigMBean {
    private final ConfigService configService;

    public ConfigMBean(ConfigService configService) {
        this.configService = configService;
    }

    @ManagedAttribute(description = "Set reload attribute")
    public void setReloadPeriod(long reloadPeriod){
        configService.setReloadPeriod(reloadPeriod);
    }

    @ManagedAttribute(description = "Get reload attribute")
    public long getReloadPeriod(){
        return configService.getReloadPeriod();
    }

    @ManagedOperation(description = "Get all config keys")
    public Collection<String> getConfigKey(){
        return configService.configKeys();
    }

    @ManagedOperation
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "Get a config value")
    })
    public String getConfig(String key){
        return configService.getConfig(key);
    }

    @ManagedOperation(description = "Set a value for config with the key")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "Config key"),
            @ManagedOperationParameter(name = "value", description = "Config value")
    })
    public void setConfig(String key, String value){
        configService.setConfig(key, value);
    }
}
