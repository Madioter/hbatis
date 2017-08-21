/**
 * @Title: FilterHandler.java
 * @Package com.madiot.hbatis.filter
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.filter;

import org.apache.hadoop.hbase.filter.Filter;

/**
 * @ClassName: FilterHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public interface FilterHandler {
    Filter getFilter(Object... param);
}
