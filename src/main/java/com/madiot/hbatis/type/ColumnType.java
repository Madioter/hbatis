/**
 * @Title: ColumnType.java
 * @Package com.madiot.hbatis.type
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.type;

import org.apache.commons.lang.ObjectUtils;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @ClassName: ColumnType
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class ColumnType {

    private String family;

    private String qualifier;

    public ColumnType(String family, String qualifier) {
        this.family = family;
        this.qualifier = qualifier;
    }

    public String getFamily() {
        return family;
    }

    public byte[] getFamilyBytes() {
        return Bytes.toBytes(this.family);
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public byte[] getQualifierBytes() {
        if (this.qualifier != null) {
            return Bytes.toBytes(this.qualifier);
        } else {
            return null;
        }
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public String toString() {
        if (this.qualifier != null) {
            return family + ":" + qualifier;
        } else {
            return family;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ColumnType && this.family.equals(((ColumnType) obj).getFamily())
                && ObjectUtils.equals(this.qualifier, ((ColumnType) obj).getQualifier());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
