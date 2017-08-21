/**
 * @Title: BaseComparsionFilterHandler.java
 * @Package com.madiot.hbatis.filter.comparsion
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 * @version
 */
package com.madiot.hbatis.filter.comparison;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.filter.BaseFilterHandler;
import com.madiot.hbatis.type.ColumnType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BaseComparsionFilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/17
 */
public abstract class BaseComparisonFilterHandler extends BaseFilterHandler {

    @Override
    public Filter getFilter(Object... param) {
        ColumnType columnType = null;
        CompareFilter.CompareOp compareFilterOp = null;
        Comparable comparable = null;
        Map<String, ParamProxy> params = new HashMap<>();
        if (ArrayUtils.isNotEmpty(param)) {
            for (Object paramItem : param) {
                if (paramItem == null) {
                    continue;
                }
                if (paramItem instanceof ColumnType) {
                    columnType = (ColumnType) paramItem;
                } else if (paramItem instanceof CompareFilter.CompareOp) {
                    compareFilterOp = (CompareFilter.CompareOp) paramItem;
                } else if (paramItem instanceof Comparable) {
                    comparable = (Comparable) paramItem;
                } else if (paramItem instanceof Map) {
                    params.putAll((Map) paramItem);
                }
            }
        }
        return getCurrentFilter(columnType, compareFilterOp, comparable, params);
    }

    public abstract Filter getCurrentFilter(ColumnType columnType, CompareFilter.CompareOp compareFilterOp, Comparable comparator, Map<String, ParamProxy> param);
}
