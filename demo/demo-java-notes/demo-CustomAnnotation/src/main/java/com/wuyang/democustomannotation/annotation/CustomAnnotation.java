package com.wuyang.democustomannotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，可用于标记类或方法
 * 
 * 该注解可用于ElementType.TYPE和ElementType.METHOD类型的元素，
 * 并在运行时保留，可以通过反射机制获取。
 * 
 * 示例用法：
 * <pre>
 * &#064;CustomAnnotation
 * public class MyClass {
 *     
 *     &#064;CustomAnnotation
 *     public void myMethod() {
 *         // 方法实现
 *     }
 * }
 * </pre>
 * 
 * @since 1.0
 */
// 指定注解可以应用的目标元素类型为TYPE和METHOD，表示该注解可以用于类、接口或方法上
@Target({ElementType.TYPE, ElementType.METHOD})
// 指定注解的保留策略为RUNTIME，表示该注解在运行时仍然有效，可以通过反射机制获取到该注解
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotation {
    /**
     * 指定要过滤的字段名
     * 默认为空，表示不过滤
     */
    String field() default "";
    /**
     * 指定要过滤掉的值
     * 默认为空，表示不过滤
     */
    String value() default "";
}

