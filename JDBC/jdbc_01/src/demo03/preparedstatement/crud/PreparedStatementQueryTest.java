package demo03.preparedstatement.crud;

import demo03.bean.Customer;
import demo03.utils.JDBCUtils;
import org.junit.Test;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
* 通用的查询操作（泛型，反射）
* */
public class PreparedStatementQueryTest {

    @Test
    public void test1() throws Exception {
        String sql = "SELECT `name`,email, birth FROM Customers";
        Class<Customer> clazz = (Class<Customer>) Class.forName("demo03.bean.Customer");
        List<Customer> list = getInstances(sql, clazz);
        for (Object obj : list){
            System.out.println(obj);
        }
    }
    public static <T> List<T> getInstances(String sql, Class<T> clazz, Object ...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JDBCUtils.getConnection();
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
            JDBCUtils.closeResource(conn, ps,rs);
        }
        return null;
    }
}
