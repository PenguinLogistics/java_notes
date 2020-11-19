package FileClass;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileTest {
    @Test
    public void test1(){
        File file1 = new File("hello.txt");
        File file2 = new File("E:\\git_repository\\java_notes\\JavaSenior\\FileIO");
        System.out.println(file1);
        System.out.println(file2);
        File file3 = new File("E:\\git_repository\\java_notes", "javaSenior");
        File file4 = new File(file3, "hi.txt");
    }

    @Test
    public void test2(){
        File file1 = new File("hello.txt");
        File file2 = new File("E:\\git_repository\\java_notes\\JavaSenior\\FileIO\\hi.txt");

        System.out.println(file1.getAbsolutePath());
        System.out.println(file1.getPath());
        System.out.println(file1.getName());
        System.out.println(file1.getParent());
        System.out.println(file1.length());
        System.out.println(file1.lastModified());

        System.out.println();

        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.getPath());
        System.out.println(file2.getName());
        System.out.println(file2.getParent());
        System.out.println(file2.length());
        System.out.println(file2.lastModified());
    }

    @Test
    public void test3(){
        File file1 = new File("hello.txt");
        File file2 = new File("src\\moved.txt");
        file2.renameTo(file1);
    }

    @Test
    public void test4() throws IOException {
        File f1 = new File("hi.txt");
        if (! f1.exists()) {
            f1.createNewFile();
            System.out.println("创建成功");
        } else {
            f1.delete();
            System.out.println("删除成功");
        }
    }

    @Test
    public void test5(){
        File f1 = new File("E:\\git_repository\\java_notes\\JavaSenior\\FileIO\\io1");
        boolean mkdir = f1.mkdir();
        if (mkdir){
            System.out.println("mkdir 创建成功");
        }
        File f2 = new File("E:\\git_repository\\java_notes\\JavaSenior\\FileIO\\io2\\io3");
        boolean mkdir1 = f2.mkdirs();
        if (mkdir1){
            System.out.println("mkdirs 创建成功");
        }
    }
}
