package Concurrency;
/*
* 同步代码块 继承Thread类
* */
public class WindowTest2 {
    public static void main(String[] args) {
        Window2 t1 = new Window2();
        Window2 t2 = new Window2();
        Window2 t3 = new Window2();
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}

class Window2 extends Thread{
    private static int ticket = 100;
//    多线程必须共用通一个锁，因此不能把非静态obj或者this作为锁，可以传一个static的object 或者 .class (class也是对象)
//    private static Object obj = new Object();
    @Override
    public void run() {
        while (true){
//            synchronized (obj){
            synchronized (Window2.class) {// Window.class   Object.class    Math.class
                if (ticket > 0) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":买票，票号为： " + ticket);
                    ticket--;
                }
                else {
                    break;
                }
            }
        }
    }
}