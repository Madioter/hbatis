/**
 * @Title: ConfigurationMap.java
 * @Package com.madiot.hbatis.datasource
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.datasource.configset;

import com.madiot.hbatis.datasource.ConfigurationSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ConfigurationMap
 * @Description: 自定义配置
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class ConfigurationMap implements ConfigurationSet {

    private HashMap<String, String> configurations = new HashMap<String, String>();

    public void set(String key, String value) {
        configurations.put(key, value);
    }

    public Map<String, String> getConfigurations() {
        return this.configurations;
    }
}
