package demo01.transaction;

//import demo03.utils.JDBCUtils;
import demo01.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/*
* 1.什么是数据库事务
* 事务：一组逻辑操作单元，使数据从一种状态变换到另一种状态
*       > 一组逻辑单元：一个或多个 DML操作
*
* 2.事务的处理原则：保证所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式。
* 当在一个事务中执行多个操作时，要么所有的操作都被提交(commit)，那么这些修改就永久地保存下来；
* 要么数据库管理系统将放弃所做的所有修改，整个事务回滚(rollback)到最初状态
*
* 3.数据一旦提交，就不可回滚
*
* 4.哪些操作会导致数据自动提交
*   > DDL操作一旦执行，都会自动提交
*       > set autocommit=false 对DDL操作失效
*   > DML默认情况下，一旦执行，就会自动提交
*       > 通过 set autocommit=false 的方式取消DML操作的自动提交
*   > 默认在关闭连接时，会自动提交数据
* */
public class TransactionTest {
    //考虑事务的转账操作
    @Test
    public void testUpdateWithTxn(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            //1. 取消数据的自动提交
            conn.setAutoCommit(false);

            String sql1 = "update user_table set balance=balance-100 where user=?";
            update(conn, sql1, "AA");

            String sql2 = "update user_table set balance=balance+100 where user=?";
            update(conn, sql2, "BB");
            System.out.println("转账成功");

            //2. 提交数据
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    //通用的增删改操作----v2.0(考虑上事务)
    public static int update(Connection conn,String sql, Object... args){//sql占位符的个数与可变形参的长度相同
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);//mysql的index从1开始
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps);
        }
        return 0;
    }

}
