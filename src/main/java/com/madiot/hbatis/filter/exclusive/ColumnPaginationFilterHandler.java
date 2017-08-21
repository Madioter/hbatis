/**
 * @Title: ColumnPagination.java
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
import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.hbase.filter.ColumnPaginationFilter;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.Map;

/**
 * @ClassName: ColumnPagination
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public class ColumnPaginationFilterHandler extends BaseExclusiveFilterHandler {

    @Override
    public Filter getCurrentFilter(Map<String, ParamProxy> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey("limit")) {
            Object limitObj = params.get("limit").getValue(Integer.class);
            Integer limit;
            if (limitObj instanceof Integer) {
                limit = (Integer) limitObj;
            } else {
                limit = NumberUtils.toInt(limitObj.toString());
            }
            Integer offset = null;
            byte[] columnOffset = null;
            if (params.containsKey("columnOffset")) {
                columnOffset = params.get("columnOffset").getByteArray(String.class);
            } else {
                Object offsetObj = params.get("offset").getValue(Integer.class);
                if (offsetObj instanceof Integer) {
                    offset = (Integer) offsetObj;
                } else {
                    offset = NumberUtils.toInt(offsetObj.toString());
                }
            }

            if (offset != null) {
                return new ColumnPaginationFilter(limit, offset);
            } else if (columnOffset != null) {
                return new ColumnPaginationFilter(limit, columnOffset);
            }
        }
        throw new FilterConfigException("ColumnPaginationFilter config error, cause: param(limit,offset) must not be null");
    }
}
