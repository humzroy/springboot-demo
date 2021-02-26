package com.example.demo.common.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhengzhen
 * @date 2021/02/26 11:16
 **/

public class RealTimePropertySourceReader {

    private final List<String> configFiles;
    private final ResourceLoader resourceLoader;
    private final PropertySourceLoader propertySourceLoader;
    private static final Logger LOG = LoggerFactory.getLogger(RealTimePropertySourceReader.class);
    private final Map<String, Long> lastModified = new HashMap<>();
    private final List<PropertySource<?>> propertySources = new ArrayList<>(8);

    public RealTimePropertySourceReader(List<String> configFiles) {
        this(configFiles, new DefaultResourceLoader());
    }

    public RealTimePropertySourceReader(List<String> configFiles, ResourceLoader resourceLoader) {
        this(configFiles, resourceLoader, new YamlPropertySourceLoader());
    }

    public RealTimePropertySourceReader(List<String> configFiles, ResourceLoader resourceLoader, PropertySourceLoader propertySourceLoader) {
        this.configFiles = configFiles;
        this.resourceLoader = resourceLoader;
        this.propertySourceLoader = propertySourceLoader;
    }

    public String getString(String key, String def) {
        Object obj = this.get(key);
        if (obj != null) {
            return obj.toString();
        }
        return def;
    }

    public Object get(String key) {
        configFiles.forEach(filePath -> {
            try {
                Resource res = this.resourceLoader.getResource(filePath);
                if (lastModified.containsKey(filePath) && lastModified.get(filePath) == res.lastModified()) {
                    return;
                }
                propertySources.removeIf(propertySource -> propertySource.getName().contains(filePath));
                lastModified.put(filePath, res.lastModified());
                propertySources.addAll(
                        this.propertySourceLoader.load(filePath, res)
                );
                if (LOG.isDebugEnabled()) {
                    LOG.debug("load config: {}", filePath);
                }
            } catch (IOException e) {
                LOG.warn("Read Config: {} Exception!", filePath, e);
            }
        });
        for (PropertySource<?> p : propertySources) {
            Object obj = p.getProperty(key);
            if (obj != null) {
                return obj;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // private final RealTimePropertySourceReader confReader = new RealTimePropertySourceReader(Lists.newArrayList("classpath:application.yml"));
        // String firstDay = confReader.getString("firstDay", "2020-01-06");

    }

}