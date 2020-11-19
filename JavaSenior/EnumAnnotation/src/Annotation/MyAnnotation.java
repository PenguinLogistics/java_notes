package Annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE, TYPE_PARAMETER, TYPE_USE})
@Inherited
@Repeatable(MyAnnotations.class)
public @interface MyAnnotation {
    String value() default "hello";
}
