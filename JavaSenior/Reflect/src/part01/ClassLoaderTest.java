package part01;

import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

public class ClassLoaderTest {

    @Test
    public void test1(){
        //对于自定义类，使用系统类加载器
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);    //sun.misc.Launcher$AppClassLoader@18b4aac2

        ClassLoader classLoader1 = classLoader.getParent();
        System.out.println(classLoader1);   //sun.misc.Launcher$ExtClassLoader@279f2327

        ClassLoader classLoader2 = classLoader1.getParent();
        System.out.println(classLoader2);   //null
    }

    @Test
    public void test2() throws Exception{
        Properties pros = new Properties();
        //读取配置文件的方式一
        //此时文件默认在当前module下
//        FileInputStream fis = new FileInputStream("jdbc.properties");
//        pros.load(fis);

        //读取配置文件的方式二：使用 ClassLoader
        //配置文件默认识别为当前 module 的src下
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("part01/jdbc1.properties");
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        System.out.println("user = "+ user + ",password = "+password);

        is.close();
//        fis.close();
    }
}
