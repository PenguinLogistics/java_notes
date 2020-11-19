package StringClass;

import org.junit.Test;

public class StringBufferBuilderTest {
    @Test
    public void test1(){
        StringBuffer sb1 = new StringBuffer("abc");
        sb1.append(1).append(2);
        System.out.println(sb1);
    }
}
