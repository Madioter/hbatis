/**
 * @Title: InclusiveStopFilterHandler.java
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
import org.apache.hadoop.hbase.filter.InclusiveStopFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

/**
 * @ClassName: InclusiveStopFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class InclusiveStopFilterHandler extends BaseExclusiveFilterHandler {
    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey("stopRowKey")) {
            return new InclusiveStopFilter(params.get("stopRowKey").getByteArray(String.class));
        }
        throw new FilterConfigException("InclusiveStopFilter config error, cause: param(stopRowKey) must not be null");
    }
}
