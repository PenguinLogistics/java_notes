package Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
* 动态代理的举例
*
*/
interface Human{
    String getBelief();
    void eat(String food);
}
//被代理类
class SuperMan implements Human{

    @Override
    public String getBelief() {
        return "I believe I can fly";
    }

    @Override
    public void eat(String food) {
        System.out.println("我喜欢吃" + food);
    }
}
/*
要想实现动态代理，需要解决的问题？
问题一：如何根据加载到内存中的被代理类，动态的创建一个代理类及其对象？
问题二：当通过代理类对象调用方法，如何动态的去调用被代理类中的同名方法？
*/
class ProxyFactory{
    //调用此静态方法，返回一个代理类对象。解决问题一
    //1. 调用Proxy.newProxyInstance() 来获得一个 Proxy类的实例，发现需要一个参数类型为 InvocationHandler (接口)，但是目前没有，需要创建一个实现InvocationHandler接口的类的对象
        /*newProxyInstance，方法有三个参数：
        loader: 用哪个类加载器去加载代理对象
        interfaces:动态代理类需要实现的接口
        h:动态代理方法在执行时，会调用h里面的invoke方法去执行*/
    //2. 创建一个 自己实现InvocationHandler接口的类 的对象，因为 Proxy.newProxyInstance()静态方法有 一个InvocationHandler形参
    //目前没有写类实现 InvocationHandler接口，去写一个
    public static Object getProxyInstance(Object obj){
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.bind(obj);
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }
}
//3. 自己写一个类实现InvocationHandler接口，InvocationHandler接口要求实现 invoke()方法，invoke() 方法要求效果和调用 被代理类的同名方法一样，
// 因此在invoke()中调用被代理类的method (形参) 的 method.invoke(obj, args)，这时需要obj
// 发现没有obj，所以给MyInvocationHandler声明一个局部变量 obj，代表被代理类对象，声明bind(obj) 方法给局部变量obj赋值
/*invoke三个参数：
        proxy：就是代理对象，newProxyInstance方法的返回对象 //问题来了：为什么要写有proxy参数呢？invoke里完全没用到啊
        method：调用的方法
        args: 方法中的参数*/
class MyInvocationHandler implements InvocationHandler{
    private Object obj;//需要使用被代理类对象赋值
    public void bind(Object obj){
        this.obj = obj;
    }
    //当我们通过代理类对象，调用方法a，就会自动调用如下方法 invoke()
    //将被代理类要执行的方法a的功能声明在invoke中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method: 即为代理类对象调用的方法，此方法也是被代理类对象要调用的方法
        Object invoke = method.invoke(obj, args);
        return invoke;
    }
}

public class ProxyTest {
    public static void main(String[] args) {
        SuperMan superMan = new SuperMan();
        //proxyInstance: 代理类对象
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(superMan);
        System.out.println(proxyInstance.getBelief());//I believe I can fly
        proxyInstance.eat("四川麻辣烫");//我喜欢吃四川麻辣烫
        System.out.println("====================================");

        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        ClothFactory proxyInstance1 = (ClothFactory) ProxyFactory.getProxyInstance(nikeClothFactory);
        proxyInstance1.produceCloth(); //Nike工厂生产一批运动服
    }
}
//尝试自己实现。。。好像还是只能用Proxy.newProxyInstance() 来添加接口，当然 Proxy.newProxyInstance 也是创建了实现接口的类(代理类) 并将它实例化(因为newProxyInstance的返回值就是
//不过Proxy的源码还是看不太懂。。。
/*class ProxyFactory1{
    private Object proxied;
    public static Object getProxyInstance(Object obj){
        Class clazz = obj.getClass();
        Class[] interfaces = clazz.getInterfaces();
    }
}*/
