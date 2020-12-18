package demo04.exer;


import demo03.preparedstatement.crud.PreparedStatementQueryTest;
import demo03.preparedstatement.crud.PreparedStatementUpdateTest;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;

//课后练习2
public class Exer2Test {
    //问题1：向 examstudents 表中添加一条记录
    /*public static void main(String[] args) {
        testInsert();
    }*/
    @Test
    public void testInsert(){
        Scanner in = new Scanner(System.in);
        System.out.println("四级/六级：");
        int type = in.nextInt();
        in.nextLine();
        System.out.println("身份证号：");
        String IDCard = in.nextLine();
        System.out.println("准考证号：");
        String examCard = in.nextLine();
        System.out.println("学生姓名：");
        String studentName = in.nextLine();
        System.out.println("所在城市：");
        String location = in.nextLine();
        System.out.println("考试成绩：");
        int grade = in.nextInt();
        in.nextLine();
        String sql = "insert into examstudents(type,IDCard,examCard,studentName,location,grade) values(?,?,?,?,?,?)";
        int updateSuccess = PreparedStatementUpdateTest.update(sql, type, IDCard, examCard, studentName, location, grade);
        if (updateSuccess >0){
            System.out.println("添加成功");
        }else{
            System.out.println("添加失败");
        }
    }
    //练习2：根据身份证号或准考证号查询学生成绩信息
    @Test
    public void queryWithIDCardOrExamCard(){
        Scanner in = new Scanner(System.in);
        System.out.println("请输入您要输入的类型：");
        System.out.println("a.准考证号");
        System.out.println("b.身份证号");
        String selection = in.nextLine();
        if ("a".equalsIgnoreCase(selection)){
            System.out.println("请输入准考证号");
            String examCard = in.nextLine();
            String sql = "select FlowID flowID, Type type, IDCard, ExamCard examCard, StudentName name, Location location, Grade grade from examstudents where examCard=?";
            //Class<Student> studentClass = (Class<Student>) Class.forName("demo04.exer.Student");
            Class<Student> studentClass = Student.class;
            List<Student> instances = PreparedStatementQueryTest.getInstances(sql, studentClass, examCard);
            if (instances.size()==0){
                System.out.println("输入的准考证号有误");
            }else {
                for (int i = 0; i < instances.size(); i++) {
                    System.out.println(instances.get(i));
                }
            }
        } else if ("b".equalsIgnoreCase(selection)){
            System.out.println("请输入身份证号");
            String IDCard = in.nextLine();
            String sql = "select FlowID flowID, Type type, IDCard, ExamCard examCard, StudentName name, Location location, Grade grade from examstudents where IDCard=?";
            //Class<Student> studentClass = (Class<Student>) Class.forName("demo04.exer.Student");
            Class<Student> studentClass = Student.class;
            List<Student> instances = PreparedStatementQueryTest.getInstances(sql, studentClass, IDCard);
            if (instances.size()==0){
                System.out.println("输入的身份证号有误");
            }else {
                for (int i = 0; i < instances.size(); i++) {
                    System.out.println(instances.get(i));
                }
            }
        } else {
            System.out.println("您的输入有误，请重新进入程序");
        }
    }
}
