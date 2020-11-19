package StringClass;

import org.junit.Test;
import sun.nio.cs.ext.GBK;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/*
* 涉及到String类与其他结构转换
* */
public class StringTest1 {
    /*
    * String 与基本数据类型，包装类之间
    * String --> 基本数据类型，包装类：调用包装类的静态方法: parseXxx(str)
    * 基本数据类型，包装类 --> String： 调用 String重载的valueOf(xxx)方法 或者 + “”
     * */

    @Test
    public void test1(){
        String str1 = "123";
        int num = Integer.parseInt(str1);// int 123

        String str2 = String.valueOf(num);// "123"
        String str3 = num + "";// "123"

        System.out.println(str1 == str3); // false str1在常量池，str3 在堆里

    }
    /*
    * String 与 char[] 转换
    * String  --> char[] : String.toCharArray()
    * char[] --> String : 调用String构造器
    * */
    @Test
    public void test2(){
        String str1 = "abc123";
        char[] charArray = str1.toCharArray();//['a','b','c','1','2','3']

        char[] arr = new char[]{'h', 'e', 'l' ,'l', 'o'};
        String str2 = new String(arr);
        System.out.println(str2); // "hello"
    }

    /*
    * String 与 Byte[]
    * 编码 : String --> Byte[] : 调用 String 的 getBytes()
    * 解码 : Byte[] --> String : 调用 String 构造器
    * */
    @Test
    public void test3() throws UnsupportedEncodingException {
        String str1 = "abc123中国";
        byte[] bytes = str1.getBytes();
//        使用默认字符集 utf-8用一个汉字3个字节
        System.out.println(Arrays.toString(bytes)); // [97, 98, 99, 49, 50, 51, -28, -72, -83, -27, -101, -67]
        byte[] gbks = str1.getBytes("gbk"); //gbk一个汉字2个字节
        System.out.println(Arrays.toString(gbks)); // [97, 98, 99, 49, 50, 51, -42, -48, -71, -6]
        String str2 = new String(bytes);
        System.out.println(str2); //abc123中国
        //乱码
        String str3 = new String(gbks);
        System.out.println(str3); // 用gbk 编码， utf-解码 ，编码集和解码集不一致

        String str4 = new String(gbks, "gbk");
        System.out.println(str4); // abc123中国

    }
}
