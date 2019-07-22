package com.aihulk.tech.entity;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * @author zhangyibo
 * @title: TestHelper
 * @projectName aihulk
 * @description: TestHelper
 * @date 2019-07-2215:04
 */
public class TestHelper {

    private static final int MAX_IDEL_CONNECTIONS = 16;

    private static final int MAX_ACTIVE_CONNECTIONS = 32;

    private static final int TIME_OUT = 1000 * 30;

    private static final String DEFAULT_ENV_ID = "default";

    private static final String URL = "jdbc:mysql://47.93.30.148:3306/aihulk?autoReconnect=true&characterEncoding=UTF8";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String USERNAME = "aihulk";

    private static final String PASSWORD = "aihulk3.1415Ss";

    private SqlSessionManager sqlSessionManager;

    public TestHelper() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        PooledDataSource dataSource = new PooledDataSource(DRIVER, URL, USERNAME, PASSWORD);
        dataSource.setPoolMaximumIdleConnections(MAX_IDEL_CONNECTIONS);
        dataSource.setPoolMaximumActiveConnections(MAX_ACTIVE_CONNECTIONS);
        dataSource.setPoolTimeToWait(TIME_OUT);
        Environment env = new Environment(DEFAULT_ENV_ID, transactionFactory, dataSource);

        //set env
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setEnvironment(env);
        configuration.addMappers("com.aihulk.tech.entity.mapper");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        sqlSessionManager = SqlSessionManager.newInstance(sqlSessionFactory);
    }

    public <T> T getMapper(Class<T> mapperClass) {
        return sqlSessionManager.getMapper(mapperClass);
    }

}
