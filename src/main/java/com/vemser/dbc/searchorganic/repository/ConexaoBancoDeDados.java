//package repository;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class ConexaoBancoDeDados {
//    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
//    private static final String PORT = "25000";
//    private static final String DATABASE = "xe";
//    private static final String USER = "VS_13_EQUIPE_1";
//    private static final String PASS = "oracle";
//    private static final String SCHEMA = "VS_13_EQUIPE_1";
//
//    public static Connection getConnection() throws SQLException {
//        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;
//
//        Connection con = DriverManager.getConnection(url, USER, PASS);
//
//        con.createStatement().execute("alter session set current_schema=" + SCHEMA);
//
//        return con;
//    }
//
//    public static void closeConnection(Connection connection) {
//        try {
//            if (connection != null)
//                connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

package com.vemser.dbc.searchorganic.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConexaoBancoDeDados {
    @Value("${spring.datasource.url}")
    private  final String SERVER;
    @Value("${spring.datasource.username}")
    private  final String USER;
    @Value("${spring.datasource.password}")
    private final  String PASS;
    @Value("${spring.jpa.properties.hibernate.default_schema}")
    private final  String SCHEMA;

    public ConexaoBancoDeDados( @Value("${spring.datasource.url}") String SERVER,  @Value("${spring.datasource.username}") String USER,  @Value("${spring.datasource.password}") String PASS,  @Value("${spring.jpa.properties.hibernate.default_schema}") String SCHEMA) {
        this.SERVER = SERVER;
        this.USER = USER;
        this.PASS = PASS;
        this.SCHEMA = SCHEMA;
    }

    public  Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(SERVER, USER, PASS);
        con.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return con;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}