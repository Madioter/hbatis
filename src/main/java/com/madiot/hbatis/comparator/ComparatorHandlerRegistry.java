/**
 * @Title: ComparatorHandlerRegistry.java
 * @Package com.madiot.hbatis
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.comparator;

import com.madiot.hbatis.io.ResolverUtil;
import com.madiot.hbatis.type.TypeException;
import com.madiot.hbatis.type.TypeHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: ComparatorHandlerRegistry
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class ComparatorHandlerRegistry {

    private final Map<String, ComparatorHandler> ALL_COMPARATOR_HANDLERS_MAP = new HashMap<>();

    public ComparatorHandlerRegistry() {
        register("binary", new BinaryComparatorHandler());
        register("binaryPrefix", new BinaryPrefixComparatorHandler());
        register("null", new NullComparatorHandler());
        register("bit", new BitComparatorHandler());
        register("regexString", new RegexStringComparatorHandler());
        register("subString", new SubStringComparatorHandler());
    }

    public void register(String filterType, ComparatorHandler comparatorHandler) {
        ALL_COMPARATOR_HANDLERS_MAP.put(filterType, comparatorHandler);
    }

    public ComparatorHandler getMappingFilterHandler(String comparatorType) {
        return ALL_COMPARATOR_HANDLERS_MAP.get(comparatorType);
    }

    public boolean hasComparatorHandler(String comparatorType) {
        return comparatorType != null && getComparatorHandler(comparatorType) != null;
    }

    public void register(Class<?> comparatorHandlerClass) {
        boolean mappedTypeFound = false;
        MappedComparators mappedTypes = comparatorHandlerClass.getAnnotation(MappedComparators.class);
        if (mappedTypes != null) {
            for (String comparatorType : mappedTypes.value()) {
                register(comparatorType, comparatorHandlerClass);
                mappedTypeFound = true;
            }
        }
        if (!mappedTypeFound) {
            register(getInstance(null, comparatorHandlerClass));
        }
    }

    public <T> void register(ComparatorHandler comparatorHandler) {
        boolean mappedTypeFound = false;
        MappedComparators mappedTypes = comparatorHandler.getClass().getAnnotation(MappedComparators.class);
        if (mappedTypes != null) {
            for (String comparatorType : mappedTypes.value()) {
                register(comparatorType, comparatorHandler);
                mappedTypeFound = true;
            }
        }
        // @since 3.1.0 - try to auto-discover the mapped type
        /*if (!mappedTypeFound && filterHandler instanceof TypeReference) {
            try {
                TypeReference<T> typeReference = (TypeReference<T>) typeHandler;
                register((Class) typeReference.getRawType(), typeHandler);
                mappedTypeFound = true;
            } catch (Throwable t) {
                // maybe users define the TypeReference with a different type and are not assignable, so just ignore it
            }
        }*/
        if (!mappedTypeFound) {
            register(null, comparatorHandler);
        }
    }

    public void register(String packageName) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
        resolverUtil.find(new ResolverUtil.IsA(TypeHandler.class), packageName);
        Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
        for (Class<?> type : handlerSet) {
            //Ignore inner classes and interfaces (including package-info.java) and abstract classes
            if (!type.isAnonymousClass() && !type.isInterface() && !Modifier.isAbstract(type.getModifiers())) {
                register(type);
            }
        }
    }

    public <T> void register(String comparatorType, Class<?> comparatorHandlerClass) {
        register(comparatorType, getInstance(comparatorType, comparatorHandlerClass));
    }

    @SuppressWarnings("unchecked")
    public ComparatorHandler getInstance(String comparatorType, Class<?> comparatorHandlerClass) {
        if (comparatorType != null) {
            try {
                Constructor<?> c = comparatorHandlerClass.getConstructor(Class.class);
                return (ComparatorHandler) c.newInstance(comparatorType);
            } catch (NoSuchMethodException ignored) {
                // ignored
            } catch (Exception e) {
                throw new TypeException("Failed invoking constructor for handler " + comparatorHandlerClass, e);
            }
        }
        try {
            Constructor<?> c = comparatorHandlerClass.getConstructor();
            return (ComparatorHandler) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("Unable to find a usable constructor for " + comparatorHandlerClass, e);
        }
    }

    @SuppressWarnings("unchecked")
    public ComparatorHandler getComparatorHandler(String type) {
        // type drives generics here
        return ALL_COMPARATOR_HANDLERS_MAP.get(type);
    }
}
