/**
 * @Title: ColumnPrefixFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.FilterConfigException;
import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.Map;

/**
 * @ClassName: ColumnPrefixFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class ColumnPrefixFilterHandler extends BaseExclusiveFilterHandler {

    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey("prefix")) {
            return new ColumnPrefixFilter(params.get("prefix").getByteArray(String.class));
        }
        throw new FilterConfigException("ColumnPrefixFilter config error, cause: param(prefix) must not be null");
    }
}
