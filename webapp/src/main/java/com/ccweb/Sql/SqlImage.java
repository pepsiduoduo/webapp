package com.ccweb.Sql;

import com.ccweb.Entity.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ccweb.query.SqlConnection.getConnection;

public class SqlImage {

    public Connection getConnection(){
        String driver="com.mysql.cj.jdbc.Driver";   //获取mysql数据库的驱动类
        String url="jdbc:mysql://csye6225-fall2019.cblkkjji2ng6.us-east-1.rds.amazonaws.com:3306/ccweb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //连接数据库（kucun是数据库名）
        String name="dbuser";//连接mysql的用户名
        String pwd="csye6225!!!!";//连接mysql的密码
        try{
            Class.forName(driver);
            Connection conn= DriverManager.getConnection(url,name,pwd);//获取连接对象
            return conn;
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void insertImage(Image image) {
        String id = image.getId();
        String url = image.getUrl();

        Connection conn = null;
        Statement stmt = null;
        List<Image> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "insert into Image (id, url) values ('"+id+"','"+url+"')";
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

    public void updateImage(String id, String url){

        Connection conn = null;
        Statement stmt = null;

        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "update Image set id ="+"'"+id+"'"+", url="+"'"+url+"'"+ "where id="+"'"+id+"'" +";";
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

    public void deleteImage(String id){

//        String id = image.getId();
//        String url = image.getUrl();

        Connection conn = null;
        Statement stmt = null;
        List<Image> result = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "delete from Image where id='"+id+"'";
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

    public Image getImageById(String id){
        Connection conn = null;
        Statement stmt = null;
        List<Image> result = new ArrayList<Image>();
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "select * from Image where id='"+id+"';";
            ResultSet res = stmt.executeQuery(sql);

            while(res.next()){
                String imageid = res.getString("id");
                String imageurl = res.getString("url");

                Image image = new Image(imageid,imageurl);
                result.add(image);
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
        return result.get(0);
    }
}
