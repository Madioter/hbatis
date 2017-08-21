/**
 * @Title: FilterHandlerRegistry.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter;

import com.madiot.hbatis.filter.comparison.DependentColumnFilterHandler;
import com.madiot.hbatis.filter.comparison.FamilyFilterHandler;
import com.madiot.hbatis.filter.comparison.QualifierFilterHandler;
import com.madiot.hbatis.filter.comparison.RowFilterHandler;
import com.madiot.hbatis.filter.comparison.SingleColumnValueExcludeFilterHandler;
import com.madiot.hbatis.filter.comparison.SingleColumnValueFilterHandler;
import com.madiot.hbatis.filter.comparison.ValueFilterHandler;
import com.madiot.hbatis.filter.decorating.SkipFilterHandler;
import com.madiot.hbatis.filter.decorating.WhileMatchFilterHandler;
import com.madiot.hbatis.filter.exclusive.ColumnCountGetFilterHandler;
import com.madiot.hbatis.filter.exclusive.ColumnPaginationFilterHandler;
import com.madiot.hbatis.filter.exclusive.ColumnPrefixFilterHandler;
import com.madiot.hbatis.filter.exclusive.FirstKeyOnlyFilterHandler;
import com.madiot.hbatis.filter.exclusive.InclusiveStopFilterHandler;
import com.madiot.hbatis.filter.exclusive.KeyOnlyFilterHandler;
import com.madiot.hbatis.filter.exclusive.PageFilterHandler;
import com.madiot.hbatis.filter.exclusive.PrefixFilterHandler;
import com.madiot.hbatis.filter.exclusive.RandomRowFilterHandler;
import com.madiot.hbatis.filter.exclusive.TimestampFilterHandler;
import com.madiot.hbatis.io.ResolverUtil;
import com.madiot.hbatis.type.TypeException;
import com.madiot.hbatis.type.TypeHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: FilterHandlerRegistry
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class FilterHandlerRegistry {

    private final Map<String, FilterHandler> ALL_FILTER_HANDLERS_MAP = new HashMap<>();

    public FilterHandlerRegistry() {
        register("rowFilter", new RowFilterHandler());
        register("familyFilter", new FamilyFilterHandler());
        register("qualifierFilter", new QualifierFilterHandler());
        register("valueFilter", new ValueFilterHandler());
        register("dependentColumnFilter", new DependentColumnFilterHandler());
        register("singleColumnValueFilter", new SingleColumnValueFilterHandler());
        register("singleColumnValueExcludeFilter", new SingleColumnValueExcludeFilterHandler());
        register("prefixFilter", new PrefixFilterHandler());
        register("pageFilter", new PageFilterHandler());
        register("keyOnlyFilter", new KeyOnlyFilterHandler());
        register("firstKeyOnlyFilter", new FirstKeyOnlyFilterHandler());
        register("inclusiveStopFilter", new InclusiveStopFilterHandler());
        register("timestampFilter", new TimestampFilterHandler());
        register("columnCountGetFilter", new ColumnCountGetFilterHandler());
        register("columnPaginationFilter", new ColumnPaginationFilterHandler());
        register("columnPrefixFilter", new ColumnPrefixFilterHandler());
        register("randomRowFilter", new RandomRowFilterHandler());
        register("skipFilter", new SkipFilterHandler());
        register("whileMatchFilter", new WhileMatchFilterHandler());
    }

    public void register(String filterType, FilterHandler filterHandler) {
        ALL_FILTER_HANDLERS_MAP.put(filterType, filterHandler);
    }

    public FilterHandler getMappingFilterHandler(String filterType) {
        return ALL_FILTER_HANDLERS_MAP.get(filterType);
    }

    public boolean hasFilterHandler(String filterType) {
        return filterType != null && getFilterHandler(filterType) != null;
    }

    public void register(Class<?> filterHandlerClass) {
        boolean mappedTypeFound = false;
        MappedFilters mappedTypes = filterHandlerClass.getAnnotation(MappedFilters.class);
        if (mappedTypes != null) {
            for (String filterType : mappedTypes.value()) {
                register(filterType, filterHandlerClass);
                mappedTypeFound = true;
            }
        }
        if (!mappedTypeFound) {
            register(getInstance(null, filterHandlerClass));
        }
    }

    public <T> void register(FilterHandler filterHandler) {
        boolean mappedTypeFound = false;
        MappedFilters mappedTypes = filterHandler.getClass().getAnnotation(MappedFilters.class);
        if (mappedTypes != null) {
            for (String filterType : mappedTypes.value()) {
                register(filterType, filterHandler);
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
            register(null, filterHandler);
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

    public <T> void register(String filterType, Class<?> typeHandlerClass) {
        register(filterType, getInstance(filterType, typeHandlerClass));
    }

    @SuppressWarnings("unchecked")
    public FilterHandler getInstance(String filterType, Class<?> filterHandlerClass) {
        if (filterType != null) {
            try {
                Constructor<?> c = filterHandlerClass.getConstructor(Class.class);
                return (FilterHandler) c.newInstance(filterType);
            } catch (NoSuchMethodException ignored) {
                // ignored
            } catch (Exception e) {
                throw new TypeException("Failed invoking constructor for handler " + filterHandlerClass, e);
            }
        }
        try {
            Constructor<?> c = filterHandlerClass.getConstructor();
            return (FilterHandler) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("Unable to find a usable constructor for " + filterHandlerClass, e);
        }
    }

    @SuppressWarnings("unchecked")
    public FilterHandler getFilterHandler(String type) {
        // type drives generics here
        return ALL_FILTER_HANDLERS_MAP.get(type);
    }
}
