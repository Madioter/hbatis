/**
 * @Title: KeyOnlyFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.KeyOnlyFilter;

import java.util.Map;

/**
 * @ClassName: KeyOnlyFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class KeyOnlyFilterHandler extends BaseExclusiveFilterHandler {
    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey("lenAsVal")) {
            Object lenAsValObj = params.get("lenAsVal").getValue(Boolean.class);
            if (lenAsValObj instanceof Boolean) {
                return new KeyOnlyFilter((Boolean) lenAsValObj);
            } else {
                return new KeyOnlyFilter(BooleanUtils.toBoolean(lenAsValObj.toString()));
            }
        } else {
            return new KeyOnlyFilter();
        }
    }
}
