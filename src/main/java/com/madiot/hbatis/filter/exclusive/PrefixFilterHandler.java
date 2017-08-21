/**
 * @Title: PrefixFilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter.exclusive;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.FilterConfigException;
import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * @ClassName: PrefixFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class PrefixFilterHandler extends BaseExclusiveFilterHandler {

    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey("prefix")) {
            return new PrefixFilter(params.get("prefix").getByteArray(String.class));
        }
        throw new FilterConfigException("PrefixFilter config error, cause: param(prefix) must not be null");
    }
}
