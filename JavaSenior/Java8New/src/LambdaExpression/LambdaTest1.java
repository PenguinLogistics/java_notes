package LambdaExpression;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

public class LambdaTest1 {
    //语法格式一：无参，无返回值
    @Test
    public void test1(){
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("我爱北京天安门");
            }
        };
        r1.run();
        //lambda表达式
        Runnable r2 = () -> System.out.println("我爱北京故宫");
        r2.run();
    }
    //语法格式二：Lambda需要一个参数 没有返回值
    @Test
    public void test2(){
        Consumer<String> con = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        con.accept("夕阳的光在绘梨衣的眼睛里缓缓地褪去");
        //lambda
        Consumer<String> con1 = (String s)->{System.out.println(s);};
        con1.accept("巨大的日轮即将什么在海平面之下");
    }
    //语法格式三：数据类型可以省略，因为可由编译器推断得出，称为“类型推断”
    @Test
    public void test3(){
        Consumer<String> con1 = (s)->{System.out.println(s);};
        con1.accept("叔叔记错了，上次那个说“叔叔喝酒”的女孩已经早已深埋在了东京郊外的那口枯井里了");
    }
    //语法格式四：Lambda若只有一个参数，小括号可以省略
    @Test
    public void test4(){
        Consumer<String> con1 = s->{System.out.println(s);};
        con1.accept("叔叔记错了，上次那个说“叔叔喝酒”的女孩已经早已深埋在了东京郊外的那口枯井里了");
    }
    //语法格式五：Lambda 需要两个或以上参数，多条执行语句，并且可以有返回值
    @Test
    public void test5(){
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println(o1);
                System.out.println(o2);
                return o1.compareTo(o2);
            }
        };

        Comparator<Integer> com2 = (o1, o2) -> {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };
        System.out.println(com2.compare(12, 21));
    }
    //语法格式六：当 Lambda 体只有一条语句，return 与大括号若有，都可以省略
    @Test
    public void test6(){
        Comparator<Integer> com2 = (o1, o2) -> o1.compareTo(o2);
        System.out.println(com2.compare(12, 21));

        Consumer<String> con1 = s-> System.out.println(s);
        con1.accept("叔叔记错了，上次那个说“叔叔喝酒”的女孩已经早已深埋在了东京郊外的那口枯井里了");
    }
    @Test
    public void test7(){
        String str;
        str = "";
        System.out.println(str);
    }
    interface my{
        void x();
    }
    abstract class a{
        protected int x;
        protected abstract void a();
    }
}
