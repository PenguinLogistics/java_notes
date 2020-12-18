package demo01.transaction;

//import demo03.utils.JDBCUtils;
import demo01.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/*
* 事物的 ACID属性
* 1. 原子性(Atomicity)
*       原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生
* 2. 一致性(Consistency)
*       事务必须使数据库从一个一致性状态变换到另一个一致性状态
* 3. 隔离性(Isolation)
*       事物的隔离性是指一个事物的执行不能被其他事务干扰，即一个事务内部的操作及使用数据对并发的其他事务是隔离的，并发执行的各个事务之间不能相互干扰
* 4. 持续性(Durability)
*       持续性是指一个事务一旦被提交，他对数据库中数据的改变是永久的，接下来的其他操作和数据库故障不应该对其有任何影响
*
* 事务的并发问题
*   脏读：T1读取了T2未提交 若T2回滚则T1读取的内容是临时且无效的
*   不可重复读：T1读取一字段，T2更新了该字段，T1再读该字段获得的值改变了
*   幻读：T1从一个表中读取一个字段，然后T2在该表插入了一些新的行。之后，T1再读取同一个表，就会多出几行
*
* 四种隔离级别(隔离级别越高，数据的一致性越好，但并发性越弱)
*   READ UNCOMMITTED    读未提交
*   READ COMMITTED      读已提交    避免脏读
*   REPEATABLE READ     可重复读    避免脏读、不可重复读
*   SERIALIZABLE        串行化     避免、不可重复读、幻读
* Oracle 支持read committed, serializable 2种隔离级别，默认 read committed
* Mysql 支持4种隔离级别，默认 repeatable read
* Mysql 查询隔离级别  select @@tx_isolation;
*       设置连接的隔离级别   set transaction isolation level read committed;
*       设置数据库系统全局隔离级别   set global transaction isolation level read committed;
* 创建用户 create user tom identified by '1234';
* 授权    grant select,insert,update,delete on test.* to tom@localhost identified by '1234';
* 所有权限 grant all privileges on *.* to tom@localhost identified by '1234';
* */
public class ACIDTest {
    @Test
    public void testTransactionSelect() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        //获取当前连接的隔离级别
        System.out.println(conn.getTransactionIsolation());
        //设置当前连接的隔离级别
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //取消自动提交
        conn.setAutoCommit(false);

        String sql = "select user,password, balance from user_table where user=?";
        List<User> users = getInstances(conn, sql, User.class, "CC");
        for (int i=0;i< users.size();i++){
            System.out.println(users.get(i));
        }
    }

    @Test
    public void testTransactionUpdate() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        //取消自动提交
        conn.setAutoCommit(false);

        String sql = "update user_table set balance = ? where user=?";
        TransactionTest.update(conn, sql, 5000, "CC");
        Thread.sleep(15000);
        System.out.println("修改结束");
    }

    //通用的查询操作，用于返回数据表中的查询结果 (version 2.0 考虑事务)
    public static <T> List<T> getInstances(Connection conn, String sql, Class<T> clazz, Object ...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);
            }
            //执行查询，得到结果集
            rs = ps.executeQuery();
            //获取结果集元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过元数据获取列数
            int columnCount = rsmd.getColumnCount();
            //
            List<T> list = new ArrayList<>();
            while (rs.next()){
                T t = (T) clazz.getDeclaredConstructor().newInstance();
                for (int i=0;i<columnCount;i++){
                    Object value = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps,rs);
        }
        return null;
    }
}
