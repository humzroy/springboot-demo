package com.example.demo.web.framework.configuration.ds.transcation;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

/**
 * @ClassName MultiDataSourceTransactionFactory
 * @Description 支持Service内多数据源切换的Factory
 * @Author wuhengzhen
 * @Date 2020-04-23 17:56
 * @Version 1.0
 */
public class MultiDataSourceTransactionFactory extends SpringManagedTransactionFactory {
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new MultiDataSourceTransaction(dataSource);
    }
}
