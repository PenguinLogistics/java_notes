package part01;

import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class NewInstanceTest {
    @Test
    public void test1() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<Person> clazz = Person.class;
        /*
        * newInstance() 调用此方法，创建对应的运行时类对象
        * */
        Person obj = clazz.getDeclaredConstructor().newInstance();
        System.out.println(obj);
    }
    //体会反射的动态性
    @Test
    public void test2(){
        for (int i=0;i<10;i++) {
            int num = new Random().nextInt(3); //0 1 2
            String classPath = "";
            switch (num) {
                case 0:
                    classPath = "java.util.Date";
                    break;
                case 1:
                    classPath = "java.lang.Object";
                    break;
                case 2:
                    classPath = "Person";
                    break;
            }
            try {
                Object obj = getInstance(classPath);
                System.out.println(obj);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
    /*
    * 创建一个指定类的对象
    * classPath：指定类的全类名
    * */
    public Object getInstance(String classPath) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(classPath);
        return clazz.newInstance();
    }
}
