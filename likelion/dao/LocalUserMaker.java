package com.likelion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LocalUserMaker implements ConnectionMaker {
    public Connection makeConnection() throws SQLException  {
        Map<String, String> env = System.getenv();
        // DB접속 (ex sql workbeanch실행)
        Connection c = DriverManager.getConnection("jdbc:mysql://ec2-3-35-226-247.ap-northeast-2.compute.amazonaws.com/likelion-db", "root", "??????");
        return c;
    }
}
