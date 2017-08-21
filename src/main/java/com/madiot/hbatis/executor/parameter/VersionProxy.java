/**
 * @Title: VersionProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.executor.parameter;

import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.mapping.structure.fragment.ColumnComp;
import com.madiot.hbatis.reflection.MetaObject;
import com.madiot.hbatis.type.TypeHandler;
import com.madiot.hbatis.type.TypeHandlerRegistry;

/**
 * @ClassName: VersionProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class VersionProxy {

    private ParamMapping<Long> timestamp;

    private ColumnComp.VersionType type;

    private Object parameter;

    public VersionProxy(ParamMapping timestamp, ColumnComp.VersionType type, Object parameter) {
        this.timestamp = timestamp;
        this.type = type;
        this.parameter = parameter;
    }

    public ColumnComp.VersionType getType() {
        return type;
    }

    public Object getParameter() {
        return parameter;
    }

    public Long getValue() {
        TypeHandlerRegistry typeHandlerRegistry = timestamp.getConfiguration().getTypeHandlerRegistry();
        TypeHandler typeHandler;
        if (timestamp.getClassType() != null) {
            typeHandler = typeHandlerRegistry.getTypeHandler(timestamp.getClassType());
        } else {
            typeHandler = typeHandlerRegistry.getTypeHandler(Long.class);
        }
        Long value = timestamp.getStaticValue(typeHandler);
        if (value != null) {
            return value;
        }
        if (parameter == null) {
            value = null;
        } else {
            MetaObject metaObject = timestamp.getConfiguration().newMetaObject(parameter);
            value = (Long) metaObject.getValue(timestamp.getPropertyName());
        }
        return value;
    }
}
