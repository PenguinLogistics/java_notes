package Concurrency;
//懒汉模式 线程安全
public class Bank {
    private Bank(){};

    private static Bank instance = null;

    public static Bank getInstance(){ //可以直接加synchronized改为同步方法
/*      方式一：效率稍差
        synchronized (Bank.class){
            if (instance == null){
                instance = new Bank();
            }
            return instance;
        }*/
//        方式二：单例模式的双重检查 效率更高
        if (instance == null) {
            synchronized (Bank.class) {
                if (instance == null) {
                    instance = new Bank();
                }
            }
        }
        return instance;
    }
}
