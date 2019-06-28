package com.aihulk.tech.decision.component;

import com.aihulk.tech.decision.config.DecisionConfig;
import com.aihulk.tech.decision.config.DecisionConfigEntity;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class MybatisService {

    private ThreadLocal<SqlSession> SQL_SESSIONS = new ThreadLocal<>();

    private final SqlSessionFactory sqlSessionFactory;

    private DecisionConfigEntity.MysqlConfig mysqlConfig = DecisionConfig.getMysqlConfig();

    private String driver = mysqlConfig.getDriver();

    private String url = mysqlConfig.getUrl();

    private String username = mysqlConfig.getUsername();

    private String password = mysqlConfig.getPassword();

    private static final int MAX_IDEL_CONNECTIONS = 16;

    private static final int MAX_ACTIVE_CONNECTIONS = 32;

    private static final int TIME_OUT = 1000 * 30;

    private static final String DEFAULT_ENV_ID = "default";

    private static final String BASE_SCAN_PACKAGE = "com.aihulk.tech.common.mapper";

    private static volatile MybatisService INSTANCE;


    public static MybatisService getInstance() {
        if (INSTANCE == null) {
            synchronized (MybatisService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MybatisService();
                }
            }
        }
        return INSTANCE;
    }

    private MybatisService() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        PooledDataSource dataSource = new PooledDataSource(driver, url, username, password);
        dataSource.setPoolMaximumIdleConnections(MAX_IDEL_CONNECTIONS);
        dataSource.setPoolMaximumActiveConnections(MAX_ACTIVE_CONNECTIONS);
        dataSource.setPoolTimeToWait(TIME_OUT);
        Environment env = new Environment(DEFAULT_ENV_ID, transactionFactory, dataSource);

        //set env
        Configuration configuration = new MybatisConfiguration();
        configuration.setEnvironment(env);
        configuration.addMappers(BASE_SCAN_PACKAGE);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public SqlSession getSqlSession() {
        SqlSession session = SQL_SESSIONS.get();
        if (session == null) {
            session = sqlSessionFactory.openSession(true);
            SQL_SESSIONS.set(session);
        }
        return session;
    }


}
