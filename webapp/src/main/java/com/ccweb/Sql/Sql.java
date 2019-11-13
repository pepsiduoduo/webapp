package com.ccweb.Sql;

import com.ccweb.Entity.Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Sql {
    public Connection getConnection(){
        String driver="com.mysql.cj.jdbc.Driver";   //获取mysql数据库的驱动类
        String url="jdbc:mysql://csye6225-fall2019.cblkkjji2ng6.us-east-1.rds.amazonaws.com:3306/ccweb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //连接数据库（kucun是数据库名）
        String name="dbuser";//连接mysql的用户名
        String pwd="csye6225!!!!";//连接mysql的密码
        try{
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(url,name,pwd);//获取连接对象
            return conn;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> getAccounts(){
        Connection conn = null;
        Statement stmt = null;
        List<Account> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT id, email, firstName, lastName, account_created, account_updated from users";
            ResultSet rs = stmt.executeQuery(sql);
            result = new ArrayList<Account>();

            while(rs.next()){
                String id = rs.getString("id");
                String email = rs.getString("email");
                String fn = rs.getString("firstName");
                String ln = rs.getString("lastName");
                //Date create_date = rs.getDate("account_created");
                //string createDateString = create_date.toString();
                String create = rs.getString("account_created").toString();
                String update = rs.getString("account_updated").toString();

                Account item =  new Account(id, email, fn, ln, create, update);
                result.add(item);


            }

            return result;

        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }


        return  result;

    }


    public Account getAccountByEmail(String email){
        Connection conn = null;
        Statement stmt = null;
        List<Account> results = new ArrayList<Account>();
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT id, email, password, firstName, lastName, account_created, account_updated from users where email='"+email+"'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String id = rs.getString("id");
                String e = rs.getString("email");
                String fn = rs.getString("firstName");
                String ln = rs.getString("lastName");
                String create = rs.getString("account_created").toString();
                String update = rs.getString("account_updated").toString();

                Account item =  new Account(id, e, fn, ln, create, update);
                item.setPassword(rs.getString("password"));
                results.add(item);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }

        int length = 0;
        length = results.size();

        if(length == 0)
            return null;
        else
            return results.get(0);

    }

    public void updateAccount(Account account){
        String email = account.getEmail();
        String password = account.getPassword();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String user_updated = time.toString();

        Connection conn = null;
        Statement stmt = null;
        List<Account> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
//            if(password!=null){
//                String sql = "update users set password = '"+password+"'/*, account_updated = '"+user_updated+"' */where email = '"+email+"'";
//                String sql1 = "update users set /*password = '"+password+"',*/ account_updated = '"+user_updated+"' where email = '"+email+"'";
//                stmt.execute(sql);
//                stmt.execute(sql1);
//
//            }
//            if(firstName!=null){
//                String sql = "insert users set firstName='"+firstName+"', account_updated='"+user_updated+"' where email='"+email+"'";
//                stmt.execute(sql);
//
//            }
//            if(lastName!=null){
//                String sql = "insert users set lastName='"+lastName+"', account_updated='"+user_updated+"' where email='"+email+"'";
//                stmt.execute(sql);
//
//            }
            //validation in account service;
            String query = String.format("update users set " +
                    "password = \"%s\", " +
                    "firstName = \"%s\", " +
                    "lastName = \"%s\", "+
                    "account_updated = \"%s\" where email = \"%s\";",
                    password,
                    firstName,
                    lastName,
                    user_updated,
                    email
                    );
            stmt.execute(query);
//            conn.commit();
        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }

    }

    public void addAccount(Account account){
        String id = account.getUserId();
        String email = account.getEmail();
        String password = account.getPassword();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String user_created = time.toString();
        //Date userCreate = calendar.getTime();
        //userCreate sql;
        Connection conn = null;
        Statement stmt = null;
        List<Account> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "insert into users (id, email, password, firstName, lastName, account_created, account_updated) values ('"+id+"','"+email+"','"+password+"','"+firstName+"','"+lastName+"','"+user_created+"','"+user_created+"')";
            //ResultSet rs = stmt.executeQuery(sql);
            //result = new ArrayList<Account>();
            stmt.execute(sql);

        }catch (SQLException ex){
            ex.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt != null){
                    conn.close();
                }
            }catch(SQLException ex){
            }
            try {
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){

            }
        }
    }

    public void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
        try{
            if(rs!=null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(ps!=null){
                ps.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
//
//    public static void main(String[] args) throws SQLException
//    {
//
//        Connection cc=Sql.getConnection();
//
//        if(!cc.isClosed())
//
//            System.out.println("Succeeded connecting to the Database!");
//        Statement statement = cc.createStatement();
//        String sql = "select * from users";
//        ResultSet rs = statement.executeQuery(sql);
//        while(rs.next()) {
//            System.out.println(rs.getString("email")+"");
//        }
//
//
//
//    }
}


