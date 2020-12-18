package demo04.connection;
import com.mchange.v2.c3p0.*;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Test {

    //方式一
    @Test
    public void testGetConnection() throws Exception {
        //获取c3p0数据库连接池（从document-quickstart上抄的）
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=PRC" );
        cpds.setUser("root");
        cpds.setPassword("1234");
        //通过设置相关的参数，对数据库连接池进行管理
        cpds.setInitialPoolSize(10);//设置初始时数据库连接池中的连接数

        Connection conn = cpds.getConnection();
        System.out.println(conn);

        DataSources.destroy(cpds);
    }

    //方式二 使用配置文件
    @Test
    public void testGetConnection2() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }
}
