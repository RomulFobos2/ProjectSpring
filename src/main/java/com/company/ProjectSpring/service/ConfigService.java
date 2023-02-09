package com.company.ProjectSpring.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigService {
    private long reloadPeriod = 300L;
    private Map<String, String> config = new HashMap<>();

    public Collection<String> configKeys(){
        return new ArrayList<>(config.keySet());
    }

    public String getConfig(String key){
        return config.get(key);
    }

    public void setConfig(String key, String value){
        config.put(key, value);
    }

    public long getReloadPeriod() {
        return reloadPeriod;
    }

    public void setReloadPeriod(long reloadPeriod) {
        this.reloadPeriod = reloadPeriod;
    }
}
