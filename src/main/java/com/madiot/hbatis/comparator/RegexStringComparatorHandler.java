/**
 * @Title: RegexStringComapratorHandler.java
 * @Package com.madiot.hbatis.comparator
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.comparator;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import org.apache.hadoop.hbase.filter.RegexStringComparator;

/**
 * @ClassName: RegexStringComapratorHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public class RegexStringComparatorHandler extends BaseComparatorHandler {

    @Override
    public Comparable getComparator(ParamProxy paramProxy, String... param) {
        return new RegexStringComparator((String) paramProxy.getValue(String.class));
    }
}
