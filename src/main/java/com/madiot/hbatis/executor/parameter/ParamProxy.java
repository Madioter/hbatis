/**
 * @Title: ParamProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.executor.parameter;

import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.reflection.MetaObject;
import com.madiot.hbatis.type.TypeHandler;
import com.madiot.hbatis.type.TypeHandlerRegistry;

/**
 * @ClassName: ParamProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class ParamProxy<T> {

    private ParamMapping<T> paramDef;

    private Object parameter;

    public ParamProxy(ParamMapping paramDef, Object parameter) {
        this.paramDef = paramDef;
        this.parameter = parameter;
    }

    public T getValue(Class<?> clz) {
        if (paramDef == null) {
            return null;
        }
        if (clz == null) {
            clz = paramDef.getClassType();
        }
        TypeHandlerRegistry typeHandlerRegistry = paramDef.getConfiguration().getTypeHandlerRegistry();
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(clz);
        T value;
        if (typeHandler != null) {
            value = (T) paramDef.getStaticValue(typeHandler);
            if (value != null) {
                return value;
            }
        }
        if (parameter == null) {
            value = null;
        } else if (typeHandlerRegistry.hasTypeHandler(parameter.getClass())) {
            value = (T) parameter;
        } else {
            MetaObject metaObject = paramDef.getConfiguration().newMetaObject(parameter);
            value = (T) metaObject.getValue(paramDef.getPropertyName());
        }
        return value;
    }

    public TypeHandler getTypeHandler(Class clz) {
        TypeHandler handler = null;
        if (paramDef != null && paramDef.getJavaType() != null) {
            handler = paramDef.getConfiguration().getTypeHandlerRegistry().getTypeHandler(paramDef.getClassType());
        }
        if (handler != null) {
            return handler;
        } else if (clz != null) {
            return paramDef.getConfiguration().getTypeHandlerRegistry().getTypeHandler(clz);
        } else {
            return paramDef.getConfiguration().getTypeHandlerRegistry().getUnknownTypeHandler();
        }
    }

    public byte[] getByteArray(Class clz) {
        if (paramDef == null) {
            return null;
        }
        if (clz == null) {
            clz = paramDef.getClassType();
        }
        TypeHandlerRegistry typeHandlerRegistry = paramDef.getConfiguration().getTypeHandlerRegistry();
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(clz);
        T value;
        if (typeHandler != null) {
            value = (T) paramDef.getStaticValue(typeHandler);
            if (value != null) {
                return typeHandler.getByteArray(value);
            }
        }
        if (parameter == null) {
            value = null;
        } else if (typeHandlerRegistry.hasTypeHandler(parameter.getClass())) {
            value = (T) parameter;
        } else {
            MetaObject metaObject = paramDef.getConfiguration().newMetaObject(parameter);
            value = (T) metaObject.getValue(paramDef.getPropertyName());
        }
        if (value != null) {
            if (typeHandler == null) {
                typeHandler = paramDef.getConfiguration().getTypeHandlerRegistry().getTypeHandler(value.getClass());
            }
            return typeHandler.getByteArray(value);
        }
        return new byte[0];
    }
}
