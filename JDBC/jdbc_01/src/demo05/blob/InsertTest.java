package demo05.blob;

import demo03.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/*
 * 使用 PreparedStatement 实现批量的数据操作
 *
 * update delete 本身就具有批量操作的效果
 * 此时批量操作主要指批量插入。使用 PreparedStatement 如何实现更高效的批量操作
 * 要求：向goods表中插入20000条数据
 * CREATE TABLE goods(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(25)
   ) CHARSET=utf8; //创建表
 * 方式1:使用statement
 *  Connection connection = JDBCUtils.getConnection();
    Statement st = connection.createStatement();
    for (int i=1;i<=20000;i++){
        String sql = "insert into goods(name) values('name" + i + "')";
        st.execute(sql);
    }
*/
public class InsertTest {
    //批量插入的方式2：使用PreparedStatement
    @Test
    public void testInsert1() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();

            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            ps = connection.prepareStatement(sql);
            for (int i=1;i<=20000;i++){
                ps.setObject(1, "name_"+i);
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+ (end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps);
        }
    }
    /*
    批量插入的方式3：
    1. addBatch() executeBatch() clearBatch()
    2. mysql 服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的的支持
        ?rewriteBatchedStatements=true 写在配置文件的url后面
    3. (5.7以后忽略)
    * */
    @Test
    public void testInsert2() {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();

            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            ps = connection.prepareStatement(sql);
            for (int i=1;i<=1000000;i++){//5525
                ps.setObject(1, "name_"+i);
                //1.攒sql
                ps.addBatch();
                if(i%500==0){
                    //2.执行
                    ps.executeBatch();
                    //3.清空batch
                    ps.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+ (end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps);
        }
    }
    //批量插入的方式4：
    @Test
    public void testInsert3() {//4820
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();

            connection = JDBCUtils.getConnection();

            //设置不允许自动提交数据
            connection.setAutoCommit(false);

            String sql = "insert into goods(name) values(?)";
            ps = connection.prepareStatement(sql);
            for (int i=1;i<=1000000;i++){
                ps.setObject(1, "name_"+i);
                //1.攒sql
                ps.addBatch();
                if(i%500==0){
                    //2.执行
                    ps.executeBatch();
                    //3.清空batch
                    ps.clearBatch();
                }
            }
            connection.commit();

            long end = System.currentTimeMillis();
            System.out.println("花费的时间为："+ (end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps);
        }
    }
}
