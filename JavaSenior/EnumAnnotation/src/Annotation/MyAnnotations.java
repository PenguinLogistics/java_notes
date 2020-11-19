package Annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE})
@Inherited
public @interface MyAnnotations {
    MyAnnotation[] value();
}
