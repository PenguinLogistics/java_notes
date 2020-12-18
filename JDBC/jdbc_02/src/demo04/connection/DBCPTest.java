package demo04.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.hamcrest.core.Is;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPTest {
    //测试dbcp数据库连接池技术

    //方式一
    @Test
    public void testGetConnection() throws SQLException {
        //创建了DBCP的数据库连接池
        BasicDataSource source = new BasicDataSource();
        //设置基本信息
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=PRC");
        source.setUsername("root");
        source.setPassword("1234");
        //还可以设置其他涉及数据库连接池管理的相关属性
        source.setInitialSize(10);
        source.setMaxTotal(10);
        //...

        Connection conn = source.getConnection();
        System.out.println(conn);
    }
    //方式二 使用配置文件
    @Test
    public void testGetConnection2() throws Exception{
        Properties pros = new Properties();
        //加载properties文件
        //方式1：类的加载器
        //InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        //方式2：文件流
        FileInputStream is= new FileInputStream(new File("src/dbcp.properties"));
        pros.load(is);
        BasicDataSource source = BasicDataSourceFactory.createDataSource(pros);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
