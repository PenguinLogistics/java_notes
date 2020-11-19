package Concurrency;
/*
* 同步代码块 实现runnable接口
* */
public class WindowTest {
    public static void main(String[] args) {
        Window w1 = new Window();
        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w1);
        Thread t3 = new Thread(w1);
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}

class Window implements Runnable{
    private int ticket = 100;
    Object obj = new Object();
    @Override
    public void run() {
        while(true){
            synchronized (obj) {//this  Window.class
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":买票，票号为： " + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}