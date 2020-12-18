package demo03.preparedstatement.crud;

import demo03.bean.Customer;
import demo03.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

//针对Customers表的查询操作
public class CustomerForQuery {
    //针对customers表的通用查询操作
    public Customer queryForCustomers(String sql,Object ...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            //通过ResultSetMetaData获取结果集中的列数
            if (rs.next()){
                Customer customer = new Customer();
                for (int i=0;i<columnCount;i++){
                    Object value = rs.getObject(i + 1);
                    //获取每个列的列名
                    String columnName = rsmd.getColumnName(i + 1);
                    //给customer对象指定的columnName属性，赋值为value (通过反射)
                    Field field = Class.forName("demo03.bean.Customer").getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(customer, value);
                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }
    @Test
    public void testQueryForCustomers(){
        String sql = "select id,name,birth,email from customers where id=?";
        Customer customer = queryForCustomers(sql, 3);
        System.out.println(customer);
        String sql1 = "select * from customers where name=?";
        Customer customer1 = queryForCustomers(sql1, "哪吒");
        System.out.println(customer1);
    }

    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where id=?";
            ps = conn.prepareStatement(sql);
            //填充占位符
            ps.setObject(1,4);
            //执行，并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()){//判断结果集的下一条是否有数据，如果有返回true，并指针下移；如果返回false，指针不下移，直接结束
                //获取当前这条数据的各个字段值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                //输出方式1
                //System.out.println("id= "+id+",name= "+name+",email= "+email+",birth="+birth);
                //方式2
                //Object[] data = new Object[]{id,name,email,birth};
                //方式3：将数据封装成一个对象（推荐）
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn,ps,resultSet);
        }
    }
}
