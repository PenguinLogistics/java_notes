package demo05.blob;

import demo03.bean.Customer;
import demo03.utils.JDBCUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/*
 * 除了解决Statement的拼串，sql注入问题外，preparedStatement还有哪些好处？
 * 1. PreparedStatement可以操作Blob数据，而Statement做不到
 * 2. PreparedStatement可以实现更高效的批量操作
 */
public class BlobTest {
    //向数据表customers中插入blob类型的字段
    @Test
    public void testInsert(){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into customers(name,email,birth,photo) values(?,?,?,?)";
            ps = connection.prepareStatement(sql);

            ps.setObject(1, "张志豪");
            ps.setObject(2, "zhangzh@qq.com");
            ps.setObject(3, "1992-09-08");
            FileInputStream is = new FileInputStream(new File("src/lib/baixiao.jpg"));
            ps.setBlob(4, is);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps);
        }
    }
    //查询数据表custoemrs表中的blob类型的字段
    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql="select id,name,email,birth,photo from customers where id=?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, 5);

            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

                //将blob类型的字段下载下来，以文件的方式保存在本地
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("src/lib/zhangyuhao.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1){
                    fos.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is!=null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos!=null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(connection,ps,rs);
        }
    }
}











