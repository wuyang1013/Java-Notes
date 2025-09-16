package com.wuyang.democustomannotation.aspect;

import com.wuyang.democustomannotation.annotation.CustomAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义注解切面类，用于处理带有CustomAnnotation注解的方法返回值过滤
 *
 * @since 1.0
 */
@Aspect     //声明这是一个Aspect切面类，用于定义切面逻辑
@Component
public class CustomAnnotationAspect {
    /**
     * 环绕通知，处理带有CustomAnnotation注解的方法
     *
     * @param joinPoint  切点
     * @param annotation 注解
     * @return 处理后的结果
     * @throws Throwable 异常
     */
    @Around("@annotation(annotation)")
    public Object around(ProceedingJoinPoint joinPoint, CustomAnnotation annotation) throws Throwable {
        // 执行原方法
        Object result = joinPoint.proceed();

        // 获取注解参数
        String field = annotation.field();
        String value = annotation.value();

        // 如果没有指定过滤字段或值，则直接返回原结果
        if (field.isEmpty() || value.isEmpty()) {
            return result;
        }

        // 如果结果是List类型，则进行过滤处理
        if (result instanceof List) {
            List<?> list = (List<?>) result;
            if (CollectionUtils.isEmpty(list)) {
                return list;
            }

            // 创建新的结果列表
            List<Object> filteredList = new ArrayList<>();
            for (Object item : list) {
                if (item != null) {
                    // 检查对象是否包含指定字段且字段值不等于指定值
                    if (!shouldFilterOut(item, field, value)) {
                        filteredList.add(item);
                    }
                }
            }
            return filteredList;
        }

        return result;
    }

    /**
     * 判断对象是否应该被过滤掉
     *
     * @param obj   对象
     * @param field 字段名
     * @param value 要过滤的值
     * @return true-应该被过滤掉，false-不应该被过滤掉
     */
    private boolean shouldFilterOut(Object obj, String field, String value) {
        try {
            // 获取对象的字段
            Field objField = ReflectionUtils.findField(obj.getClass(), field);
            if (objField == null) {
                return false;
            }

            // 设置字段可访问
            ReflectionUtils.makeAccessible(objField);

            // 获取字段值
            Object fieldValue = ReflectionUtils.getField(objField, obj);
            if (fieldValue == null) {
                return false;
            }

            // 检查字段值是否包含指定的过滤值
            return fieldValue.toString().contains(value);
        } catch (Exception e) {
            // 发生异常则不过滤
            return false;
        }
    }
}