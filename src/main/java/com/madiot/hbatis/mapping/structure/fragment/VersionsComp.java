/**
 * @Title: VersionsComp.java
 * @Package com.madiot.hbatis.mapping.structure.fragment
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 * @version
 */
package com.madiot.hbatis.mapping.structure.fragment;

import com.madiot.hbatis.executor.parameter.ParamProxy;
import com.madiot.hbatis.executor.proxy.GetProxy;
import com.madiot.hbatis.executor.proxy.ScanProxy;
import com.madiot.hbatis.mapping.structure.ParamMapping;
import com.madiot.hbatis.parsing.XNode;
import com.madiot.hbatis.session.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @ClassName: VersionsComp
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/14
 */
public class VersionsComp extends FragmentComp {

    private ParamMapping versions;

    private ParamMapping timestamp;

    private ParamMapping minStamp;

    private ParamMapping maxStamp;


    public VersionsComp(XNode node, Configuration configuration) {
        super(configuration);
        String versions = node.getStringAttribute("maxVersions");
        if (StringUtils.isNotBlank(versions)) {
            if (versions.toUpperCase().equals("ALL")) {
                this.versions = new ParamMapping(String.valueOf(Integer.MAX_VALUE), Integer.class, configuration);
            } else {
                this.versions = new ParamMapping(versions, Integer.class, configuration);
            }
        }
        String timeStamp = node.getStringAttribute("timestamp");
        if (StringUtils.isNotBlank(timeStamp)) {
            this.timestamp = new ParamMapping(timeStamp, Long.class, configuration);
        }
        String minStamp = node.getStringAttribute("minstamp");
        if (StringUtils.isNotBlank(minStamp)) {
            this.minStamp = new ParamMapping(minStamp, Long.class, configuration);
        }
        String maxStamp = node.getStringAttribute("maxstamp");
        if (StringUtils.isNotBlank(maxStamp)) {
            this.maxStamp = new ParamMapping(maxStamp, Long.class, configuration);
        }
    }

    public void addConditions(GetProxy currentGet, Object parameter) {
        Long timestamp = (Long) new ParamProxy(this.timestamp, parameter).getValue(Long.class);
        if (timestamp != null) {
            try {
                currentGet.getOperation().setTimeStamp(timestamp);
            } catch (IOException e) {
                configuration.getLogInstance().error("can't set timestamp for get options, rowkey:" + Bytes.toString(currentGet.getOperation().getRow())
                        + ", timestamp: " + timestamp);
            }
        }
        Long minstamp = (Long) new ParamProxy(this.minStamp, parameter).getValue(Long.class);
        Long maxstamp = (Long) new ParamProxy(this.maxStamp, parameter).getValue(Long.class);
        if (minstamp != null && maxstamp != null) {
            try {
                currentGet.getOperation().setTimeRange(minstamp, maxstamp);
            } catch (IOException e) {
                configuration.getLogInstance().error("can't set time range for get options, rowkey:" + Bytes.toString(currentGet.getOperation().getRow())
                        + ", rangeStamp: [" + minstamp + "," + maxstamp + "]");
            }
        }

        Integer versions = (Integer) new ParamProxy(this.versions, parameter).getValue(Integer.class);
        if (versions != null) {
            if (Integer.MAX_VALUE == versions) {
                currentGet.getOperation().setMaxVersions();
            } else {
                try {
                    currentGet.getOperation().setMaxVersions(versions);
                } catch (IOException e) {
                    configuration.getLogInstance().error("can't set max versions for get options, rowkey:" + Bytes.toString(currentGet.getOperation().getRow())
                            + ", max versions: [" + versions + "]");
                }
            }
        }
    }

    @Override
    public FragmentTypeEnum getType() {
        return FragmentTypeEnum.VERSIONS;
    }

    public ParamMapping getVersions() {
        return versions;
    }

    public void setVersions(ParamMapping versions) {
        this.versions = versions;
    }

    public ParamMapping getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ParamMapping timestamp) {
        this.timestamp = timestamp;
    }

    public ParamMapping getMinStamp() {
        return minStamp;
    }

    public void setMinStamp(ParamMapping minStamp) {
        this.minStamp = minStamp;
    }

    public ParamMapping getMaxStamp() {
        return maxStamp;
    }

    public void setMaxStamp(ParamMapping maxStamp) {
        this.maxStamp = maxStamp;
    }
}
