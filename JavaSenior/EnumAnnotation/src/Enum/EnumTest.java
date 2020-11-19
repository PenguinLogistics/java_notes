package Enum;/*
* 一、枚举类的使用
* 1. 枚举类的理解：类的对象只有有限个，确定的。我们称此类为枚举类
* 2. 当需要定义一组常量时，强烈建议使用枚举类
* 3. 如果枚举类中只有一个对象，则可以作为单例模式的实现方式
*
* 二、如何定义枚举类
* 方式一： jdk5.0 之前，自定义没剧烈
* 方式二： jdk5.0， 可以使用enum关键字定义枚举类
*
* 三、Enum 类中的常用方法：
*   values(): 返回枚举类型的对象数组。该方法可以很方便的遍历所有枚举值
*   valueOf(String str): 可以吧一个字符串转为对应的枚举类对象。要求字符串必须是枚举类对象的“名字”。否则运行时异常：IllegalArgumentException
*   toString(): 返回当前枚举类对象常量的名称
*
* 四、使用 enum 关键字定义枚举类实现接口
*   情况一：实现接口在enum 类中实现抽象方法
*   情况二：让每个枚举类的对象分别实现抽象方法
* */

public class EnumTest {

    public static void main(String[] args) {
        Season spring = Season.SPRING;
        System.out.println(spring);
    }
}

//自定义枚举类
class Season {
//    1. 声明Season 对象的属性
    private final String seasonName;
    private final String seasonDesc;

//    2. 私有化类的构造器
    private Season(String seasonName, String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }
//    3. 提供当前枚举类的多个对象
    public static final Season SPRING = new Season("春天", "春田井告");
    public static final Season SUMMER = new Season("夏天", "水着");
    public static final Season FALL = new Season("秋天", "秋叶原");
    public static final Season WINTER = new Season("冬天", "马冬梅");

//    4.其他诉求：获取枚举类对象的属性
    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }

//    4.其他诉求：提供toString()方法
    @Override
    public String toString() {
        return "Enum.Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesc='" + seasonDesc + '\'' +
                '}';
    }
}


