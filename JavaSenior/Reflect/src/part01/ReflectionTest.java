package part01;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    //反射之前，对Person 的操作
    @Test
    public void test1(){
        Person p1 = new Person("Tom", 12);
        p1.age = 10;
        System.out.println(p1.toString());
        p1.show();
    }
    //反射之后，对Person 的操作
    @Test
    public void test2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        //1. 通过反射 创建Person 类的对象
        Class clazz = Person.class;
        Constructor cons = clazz.getConstructor(String.class, int.class);

        Object obj = cons.newInstance("Tom", 12);
        Person p = (Person) obj;
        System.out.println(obj.toString()); //虚拟方法调用
        System.out.println(p);
        //2. 通过反射 调用对象指定的属性、方法
        //调用属性
        Field age = clazz.getDeclaredField("age");
        age.set(p, 10);
        System.out.println(p);
        //调用方法
        Method show = clazz.getDeclaredMethod("show");
        show.invoke(p );

        System.out.println("*********************************");

        //通过反射，可以调用Person类的私有结构，如 私有的 属性、方法、构造器
        //调用私有构造器
        Constructor cons1 = clazz.getDeclaredConstructor(String.class);
        cons1.setAccessible(true);
        Person p1 = (Person) cons1.newInstance("Jerry");
        System.out.println(p1);
        //调用私有属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p1, "HanMeiMei");
        System.out.println(p1);
        //调用私有方法
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        String nation = (String) showNation.invoke(p1, "中国");
        System.out.println(nation);
    }

    //获取 Class的实例 的方式
    @Test
    public void test3() throws ClassNotFoundException {
        //方式一：调用运行时类的属性：.class
        Class<Person> clazz1 = Person.class;
        System.out.println(clazz1);
        //方式二：通过运行时类的对象调用 getClass()
        Person p1 = new Person();
        Class<? extends Person> clazz2 = p1.getClass();
        //方式三：调用 Class的静态方法：forName(String classPath)
        Class<?> clazz3 = Class.forName("java.lang.String");
        //方式四：使用类的加载器：ClassLoader
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        Class<?> clazz4 = classLoader.loadClass("part01.Person");
        System.out.println(clazz4);

        System.out.println(clazz1 == clazz4);
    }

    @Test
    public void test4(){
        Class c1 = Object.class;
        Class c2 = Comparable.class;
        Class c3 = String[].class;
        Class c4 = int[][].class;
        Class c5 = ElementType.class;
        Class c6 = Override.class;
        Class c7 = int.class;
        Class c8 = void.class;
        Class c9 = Class.class;

        int[] a = new int[10];
        int[] b = new int[100];
        Class c10 = a.getClass();
        Class c11 = b.getClass();
        // 只要元素类型与纬度一样，就是同一个class
        System.out.println(c10 == c11);
    }
}























