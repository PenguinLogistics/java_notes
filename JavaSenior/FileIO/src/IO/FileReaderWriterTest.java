package IO;

import org.junit.Test;

import java.io.*;

public class FileReaderWriterTest {


    @Test
    public void testFileReader() {
        FileReader fr = null;
        try {
            File file = new File("hello.txt");

            fr = new FileReader(file);
            int data = fr.read();
            while (data != -1) {
                System.out.print((char) data);
                data = fr.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testFileReader1(){
        FileReader fr = null;

        try {
            //1. File 类的实例化
            File file = new File("hello.txt");
            //2. FileReader 流的实例化
            fr = new FileReader(file);
            //3. 读取操作
            // read(char[] cbuf): 返回每次读入cbuf数组中的字符个数，如果达到文件末尾，返回-1
            char[] cbuf = new char[5];
            int len;
            while((len = fr.read(cbuf))!= -1){
                /*
                * 方式一
                * 错误写法
                for (int i=0; i< cbuf.length;i++){
                    System.out.print(cbuf[i]);
                }
                * */
                for (int i=0; i< len;i++){
                    System.out.print(cbuf[i]);
                }
                /*
                * 方式二
                * 错误写法
                String str = new String(cbuf);
                System.out.print(str);
                * */
                String str = new String(cbuf, 0, len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            //4. 资源的关闭
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testFileWriter(){

        FileWriter fw = null;
        try {
            //1. File 类的实例化
            File file = new File("hi.txt");
            //2. FileReader 流的实例化
            fw = new FileWriter(file, true);

            //3. 写入操作
            fw.write("Good game, well played\n");
            fw.write("Cebbbbbbbbbbbbbbbbbbb\n");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //4. 资源的关闭
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testChar(){
        /*
        * java的char占 2字节 16bit， 只能放常用汉字，unicode 编码超过 2^16-1 编译不通过
        * (为什么我要做这种奇怪的事...)
        * */
        //𠧃 字UNICODE编码U+209C3，二进制: 000100000100111000011，十进制: 133571，HEX编码：F0A0A783，UTF-8: F0 A0 A7 83
//        char ch = '\uD842\uDDC3';
        String s = "\uD842\uDDC3";
        System.out.println(s);
    }
}

