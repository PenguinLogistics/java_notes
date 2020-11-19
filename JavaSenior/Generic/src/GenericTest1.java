import org.junit.Test;

public class GenericTest1 {
    @Test
    public void test1(){
        //如果定义了泛型类，实例化没有指明类的泛型，默认泛型类型为Object类
        //但是要求如果定义了类是带泛型的，建议实例化时指明泛型类型
        Order order = new Order();
        order.setOrderT(123);
        order.setOrderT("abc");

        Order<String> order1 = new Order<>("orderAA", 1001, "order:AA");
        order1.setOrderT("AA");
    }

    @Test
    public void test2(){
        SubOrder so = new SubOrder();
        so.setOrderT(1002); // 继承时指明泛型类型，则SubOrder不再是泛型类

        SubOrder1<String> so1 = new SubOrder1<>();
        so1.setOrderT("aaa");
    }
}
