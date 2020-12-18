package demo03.dao;


import demo01.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
* 封装了针对了数据表的通用操作
* */
public abstract class BaseDAO<T> {

    private Class<T> clazz = null;

//    public BaseDAO(){
//
//    }
    // 子类继承父类的代码块，子类中调用此代码块获取父类的泛型(挺难的，注意泛型和反射那两章)
    {
        //获取当前BaseDAO的子类继承的父类的泛型 (还是BaseDao)
        Type genericSuperclass = this.getClass().getGenericSuperclass();//先获取带泛型的父类
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        Type[] typeArguments = paramType.getActualTypeArguments();// 获取了父类的泛型参数
        clazz = (Class<T>) typeArguments[0];//泛型的第一个参数
    }

    //通用的增删改操作----v2.0(考虑上事务)
    public int update(Connection conn, String sql, Object... args){//sql占位符的个数与可变形参的长度相同
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

    //通用查询操作，查询一条数据 (version 2.0 考虑事务)
    public T getInstance(Connection conn, String sql, Object ...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                /*
                * clazz 是父类的泛型
                * */
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i=0;i<columnCount;i++){
                    Object value = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, value);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    //通用的查询操作，用于返回数据表中的查询结果 (version 2.0 考虑事务)
    public List<T> getInstances(Connection conn, String sql,  Object ...args) {
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
                T t = clazz.getDeclaredConstructor().newInstance();
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

    //用于查询特殊值的通用方法
    public <E> E getValue(Connection conn, String sql, Object ...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }
}
