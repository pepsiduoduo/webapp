package com.ccweb.Entity;



        import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.*;

//SpringBoot单元测试启动类注解
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Component
@Repository  //继承自@Component,作用于持久层
/**
 * 如果配置文件没有在默认目录下，使用注解@PropertySource获取，下面演示的是在多配置文件中获取相同属性名的值，以后置为准
 * 单配置文件只要一个路径参数就可以
 */
//@PropertySource({"classpath:application.properties","classpath:config/config.properties"})
public class InitSysAdminDivsions {

    @Value(value = "${folivora.datasource.driver-class-name}")
    private String driver;

    @Value(value = "${folivora.datasource.url}")
    private String url;

    @Value(value = "${folivora.datasource.username}")
    private String userName;

    @Value(value = "${folivora.datasource.password}")
    private String password;

    @PostConstruct
    public void init() throws SQLException, ClassNotFoundException{
        //连接数据库
        Class.forName(driver);
        //测试url中是否包含useSSL字段，没有则添加设该字段且禁用
        if( url.indexOf("?") == -1 ){
            url = url + "?useSSL=false" ;
        }
        else if( url.indexOf("useSSL=false") == -1 || url.indexOf("useSSL=true") == -1 )
        {
            url = url + "&useSSL=false";
        }
        Connection conn = DriverManager.getConnection(url, userName, password);
        Statement stat = conn.createStatement();
        //获取数据库表名
        ResultSet rs = conn.getMetaData().getTables(null, null, "sys_admin_divisions", null);

        // 判断表是否存在，如果存在则什么都不做，否则创建表
        if( rs.next() ){
            return;
        }
        else{
            // 先判断是否纯在表名，有则先删除表在创建表
//			stat.executeUpdate("DROP TABLE IF EXISTS sys_admin_divisions;CREATE TABLE sys_admin_divisions("
            //创建行政区划表
            stat.executeUpdate("CREATE TABLE sys_admin_divisions("
                    + "ID varchar(32) NOT NULL COMMENT '行政区划ID(行政区划代码)这里不使用32位的UUID,使用全数字的行政区域代码作为ID(如:440000)',"
                    + "TYPE varchar(50) DEFAULT NULL COMMENT '类型（1省级 2市级 3区县）',"
                    + "CODE varchar(50) DEFAULT NULL COMMENT '字母代码',"
                    + "NAME varchar(100) DEFAULT NULL COMMENT '名称',"
                    + "PINYIN varchar(100) DEFAULT NULL COMMENT '拼音',"
                    + "PARENT_ID varchar(32) DEFAULT NULL COMMENT '上级行政区划数字代码',"
                    + "IS_DISPLAY int(1) DEFAULT NULL COMMENT '是否显示( 0:否 1:是 )',"
                    + "SORT bigint(20) DEFAULT NULL COMMENT '排序标识',"
                    + "DEL_FLAG int(1) DEFAULT NULL COMMENT '删除标识(0:正常 1:已删除)',"
                    + "PRIMARY KEY (ID)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划 (省市区)';"
            );
        }
        // 释放资源
        stat.close();
        conn.close();
    }
}