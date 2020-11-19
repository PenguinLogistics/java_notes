package Enum;

public class EnumTest2 {
    /*
     * 使用enum关键字定义枚举类
     * 说明：定义的枚举类默认继承于java.lang.Enum 类
     * */
    public static void main(String[] args) {
        Season1 summer = Season1.SUMMER;
        System.out.println(Season1.class.getSuperclass());
        // toString()
        System.out.println(summer);
        // values()
        Season1[] values = Season1.values();
        for (Season1 value : values){
            System.out.println(value);
            value.show();
        }
        Thread.State[] values1 = Thread.State.values();
        for (Thread.State value : values1){
            System.out.println(value);
        }
        //valueOf(String objName): 返回枚举类中对象名是objName的对象
        Season1 s1 = Season1.valueOf("WINTER");
        System.out.println(s1);
    }
}

interface Info{
    void show();
}

//使用enum关键字
enum Season1 implements Info{
    //    1. 提供当前枚举类的对象，多个对象用',' 隔开，末尾 ';' 结束
    SPRING("春天", "春田井告"){
        @Override
        public void show() {
            System.out.println("春show");
        }
    },
    SUMMER("夏天", "水着") {
        @Override
        public void show() {
            System.out.println("夏show");
        }
    },
    FALL("秋天", "秋叶原") {
        @Override
        public void show() {
            System.out.println("秋show");
        }
    },
    WINTER("冬天", "马冬梅") {
        @Override
        public void show() {
            System.out.println("冬show");
        }
    };
    //    2. 声明Season 对象的属性
    private final String seasonName;
    private final String seasonDesc;
    //    3. 私有化类的构造器
    private Season1(String seasonName, String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    //    4.其他诉求：获取枚举类对象的属性
    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }

/*    @Override
    public void show() {
        System.out.println(this);
    }*/
    //    4.其他诉求：提供toString()方法
/*        @Override
        public String toString() {
            return "Enum.Season1{" +
                    "seasonName='" + seasonName + '\'' +
                    ", seasonDesc='" + seasonDesc + '\'' +
                    '}';
        }*/
}
