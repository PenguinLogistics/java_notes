import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenericTest {
    @Test
    public void Test1(){
        CustomerDAO dao1 = new CustomerDAO();
        dao1.add(new Customer());
        List<Customer> list = dao1.getForList(10);
    }

    @Test
    public void Test2(){
        Object obj = null;
        String str = null;
        obj = str;

        Object[] arr1 = null;
        String[] arr2 = null;
        arr1 = arr2;

        List<Object> list1 = null;
        List<String> list2 = null;
//        此时list1和list2的类型不具有子父类关系，编译不通过
//        list1 = list2;
    }

    @Test
    public void Test3(){
        List<Object> list1 = null;
        List<String> list2 = null;
        List<?> list = null;
        list = list1;
        list = list2;
//        编译通过，运行空指针异常，以为调用null的实例化方法
//        print(list1);
//        print(list2);

        List<String> list3 = new ArrayList<>();
        list3.add("AA");
        list3.add("BB");
        list3.add("CC");
        list = list3;
//        编译不通过
//        list.add("DD");
//        添加null
        list.add(null);
//        读取
        Object o = list.get(0);
        System.out.println(o);

    }

    @Test
    public void Test4(){
        List<? extends Person> list1 = new ArrayList<>();
        List<? super Person> list2 = new ArrayList<>();
        List<Student> list3 = new ArrayList<>();
        List<Person> list4 = new ArrayList<>();
        List<Object> list5 = new ArrayList<>();

        list1 = list3;
        list1 = list4;
//        编译不通过
//        list1 = list5;
//        编译不通过
//        list2 = list3;
        list2 = list4;
        list2 = list5;
//        编译不通过
//        list1.add(new Person());
//        list1.add(new Student());
        list2.add(new Person());
        list2.add(new Student());
    }

    public void print(List<?> list){
        Iterator<?> iterator = list.iterator();
        while(iterator.hasNext()){
            Object next = iterator.next();
            System.out.println(next);
        }
    }
}
class Person{}
class Student extends Person{}