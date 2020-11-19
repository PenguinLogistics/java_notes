package StringClass;

public class Exercise {
    /*
    * 1. java的传递机制：对于引用数据类型，传递的是参数的地址。
    *
    *  ！值传递机制：实际参数值的副本传入方法内，而参数本身不受任何影响
    *
    *    change方法的形参 str是一个新的变量 和 ex.str指向同一地址但不是同一变量
    * 2. String具有不变性，给str变量赋值 使其指向新的地址，该地址内储存“test ok”
    *    但原地址的值并不改变，且ex.str始终指向原地址（储存“good”）
    * */
    String str = new String("good");
    char[] ch = {'t','e','s','t'};

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
    }

    public void swap (int a, int b){
        int tmp = a;
        a = b;
        b = tmp;
    }

    public static void main(String[] args) {
        Exercise ex = new Exercise();
        ex.change(ex.str, ex.ch);
        System.out.println(ex.str); //good
        System.out.println(ex.ch); //best
        int a = 1;
        int b = 2;
        ex.swap(a, b);
        System.out.println(a);//1
        System.out.println(b);//2
    }
}
