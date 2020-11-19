package communication;
/*
* 莫名其妙的想法？？
* 当然肯定是不同步！
* 但是为什么在while里加一行输出就可以正常结束了？线程2和3也可以抢到了？
* 不加 while里的 sout("") 的时候，只有线程1抢的到，而且线程2 3不能正常退出
* */
public class WhatTheFuck {
    public static void main(String[] args) {
        WTF w = new WTF();
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

class WTF implements Runnable{
    private static int lock = 1;
    private static int ticket = 100;
    @Override
    public void run() {
        while (true){
//            System.out.print("");//????????????????????????为什么？！
            if (lock>0){
                lock--;
                System.out.print("");
//            synchronized (this){//
                if (ticket > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "：售票，票号为："+ticket);
                    ticket--;
                    lock ++;
                }else {
                    System.out.println(Thread.currentThread().getName()+"*****");
                    lock ++;
                    break;
                }
            }
        }
    }
}
