package demo01.connection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    // 方式一：driver
    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        /*
        url:http://localhost:8080/gmall/keyboard.jpg
        jdbc:mysql  协议
        localhost   ip地址
        3306        默认mysql端口号
        test        数据库名
        */
        String url = "jdbc:mysql://localhost:3306/companydb?serverTimezone=PRC";
        // 将用户名和密码封装在 Properties 中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "1234");
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }
    // 方式二：对方式一的迭代 没有使用第三方api，有更好的可移植性
    @Test
    public void testConnection2() throws Exception {
        // 1. 获取Driver的实现类对象
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.getDeclaredConstructor().newInstance();
        // 2. 提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/companydb?serverTimezone=PRC";
        // 3. 提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "1234");
        // 4. 获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }
    //方式三：使用DriverManager替换Driver
    @Test
    public void testConnection3() throws Exception {
        //1. 获取Driver的实现类对象
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.getConstructor().newInstance();
        //2. 提供两外三个连接信息
        String url = "jdbc:mysql://localhost:3306/companydb?serverTimezone=PRC";
        String user = "root";
        String password = "1234";
        // 注册驱动
        DriverManager.registerDriver(driver);
        // 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
    // 方式四：只是加载驱动，不用显示地注册驱
    // 因为
    // static {
    //        try {
    //            java.sql.DriverManager.registerDriver(new Driver());
    //        } catch (SQLException E) {
    //            throw new RuntimeException("Can't register driver!");
    //        }
    //    }
    @Test
    public void testConnection4() throws Exception {
        //2. 提供两外三个连接信息
        String url = "jdbc:mysql://localhost:3306/companydb?serverTimezone=PRC";
        String user = "root";
        String password = "1234";
        //1. 获取Driver的实现类对象
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
    //方式五(final)：将数据库连接需要地4个基本信息声明在配置文件中，然后通过读取配置文件地方式，获取连接
    //此方法的好处：
    // 实现了数据和代码的分离，实现了解耦
    // 如果需要修改配置文件信息，可以避免程序重新打包
    @Test
    public void testConnection5() throws IOException, ClassNotFoundException, SQLException {
        //1. 读取配置文件中的4个基本信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");
        //2. 加载驱动
        Class.forName(driverClass);
        //3. 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}
