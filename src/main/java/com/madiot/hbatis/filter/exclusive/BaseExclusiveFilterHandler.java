/**
 * @Title: BaseExclusiveFilterHandler.java
 * @Package com.madiot.hbatis.filter.exclusive
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.BaseFilterHandler;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BaseExclusiveFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public abstract class BaseExclusiveFilterHandler extends BaseFilterHandler {

    @Override
    public Filter getFilter(Object... param) {
        Map<String, ParamProxy> params = new HashMap<>();
        if (ArrayUtils.isNotEmpty(param)) {
            for (Object paramItem : param) {
                if (paramItem instanceof Map) {
                    params.putAll((Map) paramItem);
                }
            }
        }
        return getCurrentFilter(params);
    }

    public abstract Filter getCurrentFilter(Map<String, ParamProxy> params);
}
