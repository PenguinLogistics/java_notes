package part02;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static javax.swing.UIManager.get;

/*
    调用运行时类中指定的结构：属性 方法 构造器
*/
public class GetSpecificTest {
    @Test
    public void testField() throws Exception {
        Class clazz = Person.class;
        //创建运行时类的对象
        Person p = (Person) clazz.newInstance();
        //1. 调用属性
        //getField(String name): 要求运行时类中属性声明为public
        //通常不采用此方式
        Field id = clazz.getField("id");
        /*
        设置当前属性的值
        <Field>.set(Obj obj, Obj value): 参数1. 指明设置哪个对象的属性 参数2. 将此属性值设置为多少
        */
        id.set(p, 1001);
        /*
        获取当前属性的值
        <Field>.get()
        */
        int pid = (int) id.get(p);
        System.out.println(pid);
    }
    /*
    操作运行时类的属性
    */
    @Test
    public void testField1() throws Exception {
        Class clazz = Person.class;
        Person p = (Person) clazz.newInstance();
        //1. getDeclaredField(String name): 获取运行时类中指定变量名的属性
        Field name = clazz.getDeclaredField("name");
        //2. 保证当前属性是可访问的
        name.setAccessible(true);
        //3. 获取、设置指定对象的此属性
        name.set(p, "Tom");
        System.out.println(name.get(p));
    }
    @Test
    public void testMethod() throws Exception {
        Class<Person> clazz = Person.class;
        Person p = clazz.newInstance();
        /*
        1. 获取指定方法
        getDeclaredMethod(String name, Class<?>...ParameterTypes) 参数1. 指明获取方法的名称 参数2. 指明获取方法的形参列表
        */
        //2. 保证当前方法可访问
        Method show = clazz.getDeclaredMethod("show", String.class);
        show.setAccessible(true);
        /*
        3. 调用invoke(Object obj, Obj... args) 参数1. 方法的调用者 2. 给方法形参赋值的实参
        invoke() 的返回值即为对应类中调用方法的返回值
        */
        String returnValue = (String) show.invoke(p, "CN");
        System.out.println(returnValue);
        //private static void showDesc()
        Method showDesc = clazz.getDeclaredMethod("showDesc");
        showDesc.setAccessible(true);
        //如果调用的运行时类中的方法没有返回值，则此invoke()返回null
        Object returnVal = showDesc.invoke(Person.class);
        System.out.println(returnVal);  //null
    }
    /*
    获取constructor
    通过constructor创建运行时类对象
    //这是jdk 9 以后建议的创建运行时类对象的方式 clazz.getDeclaredConstructor().newInstance()
    */
    @Test
    public void testConstructor() throws Exception{
        Class clazz = Person.class;
        Constructor constructor = clazz.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        Person per = (Person) constructor.newInstance("Tom");
        System.out.println(per);
    }
}
