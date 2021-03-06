package io.cloud.layer.hbase.impl.service;

import io.cloud.layer.hbase.core.api.HbaseTableService;
import io.cloud.layer.hbase.core.model.HbaseTable;
import io.cloud.layer.hbase.impl.exceptions.HbaseAdminOperationException;
import io.cloud.layer.hbase.impl.factories.TableFactories;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author RippleChan
 * @date 2019-04-11 21:53
 */
@Service("hbaseTableService")
@Slf4j
public class HbaseTableServiceImpl implements HbaseTableService {

    @Autowired
    private Connection connection;


    @Override
    public Admin getAdmin() {
        try {
            return connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("获取Admin失败");
    }

    @Override
    public List<TableDescriptor> list() {
        try {
            return this.getAdmin().listTableDescriptors();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public boolean create(TableDescriptor tableDescriptor) { ;
        try {
            this.getAdmin().createTable(tableDescriptor);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public boolean create(HbaseTable hbaseTable) {
        boolean exist = this.exist(hbaseTable.getTableName());
        if (exist) {
            log.warn("表已经存在，无需再次创建");
            return false;
        }
        Admin admin = this.getAdmin();
        TableDescriptor tableDescriptor = TableFactories.buildTableDescriptor(hbaseTable);
        try {
            admin.createTable(tableDescriptor);
            return true;
        } catch (IOException e) {
            log.error("表创建失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean disable(String tableName) {
        try {
            this.getAdmin().disableTable(TableName.valueOf(tableName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public boolean enable(String tableName) {
        try {
            this.getAdmin().enableTable(TableName.valueOf(tableName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public boolean drop(String tableName) {
        try {
            this.getAdmin().deleteTable(TableName.valueOf(tableName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public boolean delete(String tableName) {
        this.disable(tableName);
        this.drop(tableName);
        return true;
    }

    @Override
    public boolean alter(TableDescriptor tableDescriptor) {
        boolean exist = this.exist(tableDescriptor.getTableName().toString());
        if (exist) {
            try {
                this.getAdmin().modifyTable(tableDescriptor);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public boolean exist(String tableName) {
        try {
            return this.getAdmin().tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public TableDescriptor describe(String tableName) {
        try {
            TableDescriptor descriptor = this.getAdmin().getDescriptor(TableName.valueOf(tableName));
            return descriptor;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }

    @Override
    public boolean truncateTable(String tableName,boolean preserveSplits) {
        try {
            this.getAdmin().truncateTable(TableName.valueOf(tableName), preserveSplits);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new HbaseAdminOperationException("操作异常");
    }


}
