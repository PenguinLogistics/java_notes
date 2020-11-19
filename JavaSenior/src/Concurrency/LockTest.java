package Concurrency;

import java.util.concurrent.locks.ReentrantLock;

//Lock锁
public class LockTest {
    public static void main(String[] args) {
        Window5 w = new Window5();

        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}

class Window5 implements Runnable{
    private int ticket = 100;
    private ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true){
            try {
//                调用锁定方法 lock()
                lock.lock();
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "：售票，票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }finally {
                lock.unlock();
            }
        }
    }
}