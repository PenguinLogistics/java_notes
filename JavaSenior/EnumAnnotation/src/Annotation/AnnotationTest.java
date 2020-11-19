package Annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import org.junit.Test;
/*
* 1. 理解Annotation
*   jdk 5.0 新增功能
*   Annotation其实就是代码里的特殊标记，这些标记在编译，类加载，运行 时被读取，并执行相应的处理。程序员可以在不改变原有逻辑的情况下，在源文件中嵌入一些补充信息
*   在javaSE 中，注解的使用目的比较简单，例如标记过时的功能，忽略警告等。在javaEE/Android 中注解占据了更重要的角色，例如用来配置应用程序的任何切面，代替javaEE旧版中所遗留的繁冗代码和xml配置等
* 2. Annotation的使用示例
*   示例一：生成文档相关的注解
*   示例二：在编译时进行格式检查（jdk内置的三个基本注解）
*       @override：限定重写父类方法，该注解只能用于方法
*       @Deprecated：用于表示修饰的 类、方法等 已过时。通常是因为所修饰的结构危险或
*       @SuppressWarnings：抑制编译器警告
*
*   示例三：跟踪代码依赖性，实现替代配置文件功能
*
* 3. 如何自定义注解：参照SuppressWarnings
*   ① 注解声明为 @interface
*   ② 内部成员通常使用 value 表示
*   ③ 可以指定成员的默认值，使用 default 定义
*   ④ 如果自定义注解没有成员，表明是一个标识作用 //比如 @Override
*   如果注解有成员，在使用注解时，需要指明成员的值(或默认值)
*   自动以注解必须配上注解的信息处理流程(使用反射)才有意义
*   自定义注解通常都会指明两个元注解：Retention Target
*
* 4. jdk 提供的4种元注解
*   元注解：对现有注解进行解释说明的注解（修饰注解的注解）
*   Retention   用于指定修饰的Annotation的生命周期：成员为RetentionPolicy 枚举类 SOURCE(.class文件中不保留注解信息)/CLASS(默认，在.class中保留 但是不会被JVM保留在运行时)/RUNTIME(保留在.class，运行时也加载到内存)
*               只有声明为RUNTIME生命周期的注解，才能通过反射获取
*   Target  用于指定被修饰的 Annotation 能用于修饰哪些程序元素
*   //出现频率较低
*   Documented  用于指定被修饰的 Annotation 类将被javadoc 工具提取成文档。默认情况下，javadoc是不包括注解的
*   Inherited   被修饰的Annotation 将具有继承性
*
* 5. 通过反射获取注解信息
*
* 6. jdk 1.8 中注解的新特性：可重复性、类型注解
*   可重复性
*       ① 在MyAnnotation 上声明 @Repeatable(Annotation.MyAnnotations.class)
*       ② Annotation.MyAnnotation 的 Retention 和 Target 和 MyAnnotations一致
*   类型注解
*       jdk 1.8 之后，元注解 @Target 的参数类型 ElementType 枚举值多了两个：TYPE_PARAMETER, TYPE_USE
*           ElementType.TYPE_PARAMETER 表示该注解能写在类型变量的声明语句中（如：泛型声明）
*           ElementType.TYPE_USE 表示该注解能写在使用类型的任何语句中
* */
public class AnnotationTest {
    // 用 @Inherited 修饰 Annotation.MyAnnotation 则使用MyAnnotation修饰的Person类具有继承性，所以Student类也有 MyAnnotation修饰
    @Test
    public void testGetAnnotation(){
        Class<Student> studentClass = Student.class;
        Annotation[] annotations = studentClass.getAnnotations();
        for (Annotation annotation : annotations){
            System.out.println(annotation);
        }
    }

}

//@Annotation.MyAnnotation(value = "hi")
//@Annotation.MyAnnotation(value = "abc")
//jdk 8 前
//@Annotation.MyAnnotations({@Annotation.MyAnnotation(value = "hi"), @Annotation.MyAnnotation(value = "abc")})
@MyAnnotation(value = "hi")
@MyAnnotation(value = "abc")
class Person{
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void walk(){
        System.out.println("人走路");
    }
    public void eat(){
        System.out.println("人恰饭");
    }
}

class Student extends Person{

    @Override
    public void walk() {
        System.out.println("学生走路");
    }
}

class Generic<@MyAnnotation T>{
    public void show(){
        ArrayList<@MyAnnotation String> list = new ArrayList<>();
        int num = (@MyAnnotation int) 10L;
    }
}