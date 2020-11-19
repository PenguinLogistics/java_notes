package StringClass;

import org.junit.Test;

public class StringTest {

    @Test
    public void test2(){
        String s1 = "javaEE";
        String s2 = "javaEE";

        String s3 = new String("javaEE");
        String s4 = new String("javaEE");

        System.out.println(s1 == s2); // true
        System.out.println(s1 == s3); // false
        System.out.println(s3 == s4); // false
        System.out.println("*************************");
        Person p1 = new Person("Tom", 12);
        Person p2 = new Person("Tom", 12);
        System.out.println(p1.name == p2.name); // true

        p1.name = "Jerry";
        System.out.println(p2.name); // Tom
    }

    /*
    * 常量与常量的拼接结果在常量池 且常量池中不会存在相同的内容
    * 只要其中有一个是变量，结果就在堆中
    * 如果拼接的结果调用intern()方法，返回值就在常量池
    *
    * */
    @Test
    public void test3(){
        String s1 = "javaEE";
        String s2 = "hadoop";
        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop";
        String s5 = s1 + "hadoop";
        String s6 = s1 + s2;
        String s7 = s6.intern();
        System.out.println(s3 == s4);//true
        System.out.println(s3 == s5);//false
        System.out.println(s5 == s6);//false
        System.out.println(s3 == s7);//true
    }
}



class Person{
    String name;
    int age;
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
}
