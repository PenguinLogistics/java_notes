package Concurrency;
/*
* 多线程的创建 方式一：继承Thread类
* 1.创建一个继承于Thread类的子类
* 2.重写Thread类的run()方法 --> 将此线程执行的操作生命在run()中
* 3.创建Thread类的子类对象
* 4.通过此对象调用start()
* */
public class demo01 {
    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        MyThread2 t2 = new MyThread2();
        Thread.currentThread().setName("主线程");
        t1.start();
        //t2.start();
        for (int i=0;i<100;i++){
            if (i%2 == 0) {
//                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
            if (i == 20){
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        for (int i=0;i<100;i++){
            if (i%2 == 0){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+ ": " + i);
            }
            if (i%20 == 20){
                yield();
            }
        }
    }
}

class MyThread2 extends Thread{
    @Override
    public void run() {
        for (int i=0;i<100;i++){
            if (i%2 == 1){
                System.out.println(Thread.currentThread().getName()+ ": " + i);
            }
        }
    }
}

