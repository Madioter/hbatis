/**
 * @Title: ComparatorHandler.java
 * @Package com.madiot.hbatis.comparator
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 * @version
 */
package com.madiot.hbatis.comparator;

import com.madiot.hbatis.executor.parameter.ParamProxy;

/**
 * @ClassName: ComparatorHandler
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/15
 */
public interface ComparatorHandler {
    Comparable getComparator(ParamProxy paramProxy, String... param);
}
