package Concurrency;
/*
* 1. 创建一个实现了runnable接口的类
* 2. 实现类中实现Runnable中的抽象方法 run()
* 3. 创建实现类的对象
* 4. 将此对象作为参数传递到Thread类构造器中，创建Thread类的对象
* 5. 通过Thread类的对象调用 start()
*
*
* */
public class demo02 {
    public static void main(String[] args) {
        MyThread3 m1 = new MyThread3();
        Thread t1 = new Thread(m1);
        t1.start();
    }
}

class MyThread3 implements Runnable{

    @Override
    public void run() {
        for (int i=0;i<100;i++){
            if (i%2 == 0){
                System.out.println(i);
            }
        }
    }
}