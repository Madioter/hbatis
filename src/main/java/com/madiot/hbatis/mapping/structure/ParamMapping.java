/**
 * @Title: ParamMapping.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 * @version
 */
package com.madiot.hbatis.mapping.structure;

import com.madiot.hbatis.io.Resources;
import com.madiot.hbatis.session.Configuration;
import com.madiot.hbatis.type.TypeHandler;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ParamMapping
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/10
 */
public class ParamMapping<T> {

    private String propertyName;
    private String javaType;
    private String staticValue;
    private Configuration configuration;

    public ParamMapping(String express, String javaType, Configuration configuration) {
        this(express, configuration);
        if (this.javaType == null) {
            this.javaType = javaType;
        }
    }

    public ParamMapping(String express, Class clz, Configuration configuration) {
        this(express, configuration);
        this.javaType = clz.getName();
    }

    public ParamMapping(String express, Configuration configuration) {
        this.configuration = configuration;
        if (!express.matches("^\\#\\{.*\\}$")) {
            this.staticValue = express;
        } else {
            Matcher matcher = Pattern.compile("^\\#\\{([^,]*?)(,javaType=(.*))?\\}$").matcher(express);
            while (matcher.find()) {
                this.propertyName = matcher.group(1).trim();
                String localJavaType = matcher.group(3);
                if (localJavaType != null) {
                    this.javaType = localJavaType.trim();
                }
            }
        }
    }

    public String getJavaType() {
        return javaType;
    }

    public Class<?> getClassType() {
        if (this.javaType != null) {
            try {
                return Resources.classForName(this.javaType);
            } catch (ClassNotFoundException e) {
                this.configuration.getLogInstance().warn("TypeHandler get FAILED , class name is "
                        + this.javaType + ",row key will used default String type handler");
            }
        }
        return null;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public T getStaticValue(TypeHandler<T> typeHandler) {
        if (typeHandler != null && this.staticValue != null) {
            try {
                return typeHandler.stringToObject(staticValue);
            } catch (ParseException e) {
                configuration.getLogInstance().error("parser exception,cause:" + e.getCause(), e);
            }
        }
        return null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
