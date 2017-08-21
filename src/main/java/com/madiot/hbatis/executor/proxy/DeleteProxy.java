/**
 * @Title: PutProxy.java
 * @Package com.madiot.hbatis.executor.proxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 * @version
 */
package com.madiot.hbatis.executor.proxy;

import com.madiot.hbatis.executor.parameter.ColumnProxy;
import com.madiot.hbatis.mapping.structure.fragment.ColumnComp;
import org.apache.hadoop.hbase.client.Delete;

/**
 * @ClassName: PutProxy
 * @Description: TODO
 * @author Yi.Wang2
 * @date 2017/8/8
 */
public class DeleteProxy implements IExecutorProxy {

    private Delete delete;

    public DeleteProxy(Delete delete) {
        this.delete = delete;
    }

    public DeleteProxy(byte[] rowKey) {
        this.delete = new Delete(rowKey);
    }

    public DeleteProxy(byte[] rowKey, long ls) {
        this.delete = new Delete(rowKey, ls);
    }

    @Override
    public void addElement(ColumnProxy columnElement) {
        Long version = null;
        ColumnComp.VersionType versionType = null;
        if (columnElement.getTimestamp() != null) {
            version = columnElement.getTimestamp().getValue();
            versionType = columnElement.getTimestamp().getType();
        }

        if (version != null) {
            if (columnElement.getColumnType().getQualifierBytes() != null) {
                if (versionType == ColumnComp.VersionType.CURRENT) {
                    // 删除指定版本的列
                    this.delete.addColumn(columnElement.getColumnType().getFamilyBytes(),
                            columnElement.getColumnType().getQualifierBytes(), columnElement.getTimestamp().getValue());
                } else if (versionType == ColumnComp.VersionType.BEFORE) {
                    // 删除列的指定版本之前的版本
                    this.delete.addColumns(columnElement.getColumnType().getFamilyBytes(),
                            columnElement.getColumnType().getQualifierBytes(), columnElement.getTimestamp().getValue());
                }
            } else {
                // 删除指定版本之前的版本列族
                this.delete.addFamily(columnElement.getColumnType().getFamilyBytes(), columnElement.getTimestamp().getValue());
            }
        } else {
            if (columnElement.getColumnType().getQualifierBytes() != null) {
                if (versionType == ColumnComp.VersionType.LASTEST) {
                    // 删除最新版本的列
                    this.delete.addColumn(columnElement.getColumnType().getFamilyBytes(),
                            columnElement.getColumnType().getQualifierBytes());
                } else {
                    // 删除列的全部版本
                    this.delete.addColumns(columnElement.getColumnType().getFamilyBytes(),
                            columnElement.getColumnType().getQualifierBytes());
                }
            } else {
                // 删除全部版本列族
                this.delete.addFamily(columnElement.getColumnType().getFamilyBytes());
            }
        }
    }

    public Delete getOperation() {
        return delete;
    }
}
