package com.likelion.dao;
import com.likelion.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;


public class UserDao {
    //AWSUserMaker awsUserMaker = new AWSUserMaker();
   /* private Connection makeConnection() throws SQLException{
        Map<String, String> env = System.getenv();
        // DB접속 (ex sql workbeanch실행)
        Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                env.get("DB_USER"), env.get("DB_PASSWORD"));
        return c;
    } */
    private ConnectionMaker connectionMaker;

    public UserDao(){
        this.connectionMaker = new AwsConnectionMaker();
    }
    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void add(User user) {
        try {
            Connection c = connectionMaker.makeConnection();

            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public User findById(String id) throws SQLException, ClassNotFoundException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement pstmt = conn.prepareStatement(
                "select * from users where id=?");
        pstmt.setString(1, id);

        ResultSet rs = pstmt.executeQuery();
        User user = null;
        if(rs.next()){
            user = new User(rs.getString("id"),
                    rs.getString("name"), rs.getString("password"));
        }

        rs.close();
        pstmt.close();
        conn.close();

        if(user == null){ throw new EmptyResultDataAccessException(1); }

        return user;
    }

    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = connectionMaker.makeConnection();
            ps = new DeleteAllStrategy().makePreparedStatement(c);
            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                }
            }
            if(c != null){
                try{
                    c.close();
                }catch (SQLException e){
                }
            }
        }
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                }
            }
            if(c != null){
                try{
                    c.close();
                }catch (SQLException e){
                }
            }
        }
    }


    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        userDao.add();
        //User user = userDao.findById("6");
        //System.out.println(user.getName());
    }
}
