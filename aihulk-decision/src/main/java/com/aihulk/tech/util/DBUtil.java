package com.aihulk.tech.util;

import com.aihulk.tech.config.DecisionConfig;
import com.aihulk.tech.config.DecisionConfigEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DBUtil
 * @Description simple database util
 * @Author yibozhang
 * @Date 2019/5/1 20:13
 * @Version 1.0
 */
@Slf4j
public class DBUtil {

    private static final DecisionConfigEntity.MysqlConfig CONFIG = DecisionConfig.getMysqlConfig();

    /**
     * mysql config
     */
    private static final String URL = CONFIG.getUrl();
    private static final String DRIVER = CONFIG.getDriver();
    private static final String USERNAME = CONFIG.getUsername();
    private static final String PASSWORD = CONFIG.getPassword();

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER); //classLoader,加载对应驱动
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static <R> List<R> exeQuery(String sql, Object... params) {
        //param check
        int paramNum = getParameterNum(sql);
        if (paramNum != params.length) {
            throw new RuntimeException("parameter's num error");
        }
        //execute sql
        ResultSet resultSet;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            bindParameters(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
            return (List<R>) mapToResult(resultSet);
        } catch (Exception e) {
            log.error("execute sql failed", e);
            throw new RuntimeException();
        }
    }

    private static int getParameterNum(String sql) {
        return sql.length() - sql.replaceAll("\\?", "").length();
    }

    private static List<Map<String, Object>> mapToResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<?> results = Lists.newArrayListWithExpectedSize(columnCount);
        while (resultSet.next()) {
            Map<String, Object> result = Maps.newHashMapWithExpectedSize(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                result.put(underlineToCamel(metaData.getColumnName(i)), resultSet.getObject(i));
            }
        }
        return null;
    }

    private Object getResultSetVal(ResultSet rs, int i) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        return null;
    }

    private static final char UNDERLINE = '_';

    private static String underlineToCamel(String column) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < column.length(); i++) {
            char c = column.charAt(i);
            if (c == UNDERLINE && i + 1 < column.length()) {
                result.append(String.valueOf(column.charAt(++i)).toUpperCase());
            } else {
                result.append(column.charAt(i));
            }
        }
        return result.toString();
    }

    private static PreparedStatement bindParameters(PreparedStatement statement, Object... params) throws SQLException {

        int paramSize = params.length;

        for (int i = 1; i <= paramSize; i++) {
            Object param = params[i - 1];
            if (param instanceof Integer) {
                statement.setInt(i, (Integer) param);
            } else if (param instanceof String) {
                statement.setString(i, (String) param);
            } else if (param instanceof Float) {
                statement.setFloat(i, (Float) param);
            } else if (param instanceof Long) {
                statement.setLong(i, (Long) param);
            } else if (param instanceof Double) {
                statement.setDouble(i, (Double) param);
            } else if (param instanceof Date) {
                statement.setDate(i, (Date) param);
            } else {
                statement.setObject(i, param);
            }
        }

        return statement;
    }

}
