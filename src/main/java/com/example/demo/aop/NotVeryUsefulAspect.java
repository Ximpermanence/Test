package com.example.demo.aop;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Student;
import com.example.demo.util.ReflectFieldUtil;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @description: 表-实体
 * @author: chenhao
 * @create:2020/8/19 14:22
 **/
@Component
@Aspect
public class NotVeryUsefulAspect {

    private static final String STR_MAPPING = "(\\()(.*?)(\\))";
    private static final String STR_FORMAT1 = "\\(";
    private static final String STR_FORMAT2 = "\\)";
    private static final Pattern pattern = Pattern.compile(STR_FORMAT1);


    @Pointcut(" execution(* com.example.demo.aop..*.*(..))")
    private void anyOldTransfer() {
    }

    @Pointcut(" execution(* com.example.demo..*.*(..))")
    private void anyOldTransfer1() {
    }

    @Before("anyOldTransfer()")
    public void advice() {
        System.out.println("--------@Before.调用方法之前-------------");
    }


    @Pointcut("execution(* com.example.demo.service.ClassService.*.*(..))")
    public void afterDemo() {
    }

    /**
     * @param joinPoint
     * @param returnValue
     * @throws Throwable
     * @after获取各项数据
     */
    @AfterReturning(value = "afterDemo()", returning = "returnValue")
    public void afterDemo(JoinPoint joinPoint, Object returnValue) throws Throwable {

        //获取参数
        Object[] objects = joinPoint.getArgs();

        //获取返回值,并将其转换JSON格式字符串
        System.out.println("END params : " + JSONObject.toJSON(returnValue));

        // 获取方法名
        String methodName = joinPoint.getSignature().getName();

        // 反射获取目标类
        java.lang.Class<?> targetClass = joinPoint.getTarget().getClass();

        // 拿到方法对应的参数类型
        java.lang.Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();

        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);

    }



    @Pointcut("execution(* com.example.demo.service..*.*(..))")
    public void aroundService() {
    }

    /**
     * 通过@around对返回值进行处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("aroundService()")
    public Object aroundDemo(ProceedingJoinPoint joinPoint) throws Throwable {

        Object object = joinPoint.proceed();

        String jsonValue = JSONObject.toJSONString(object);

        java.lang.Class<?> targetClass = joinPoint.getTarget().getClass();
        System.out.println("获取切面所在类为：" + targetClass.getName());

        Type type = targetClass.getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        java.lang.Class tClass = (java.lang.Class<T>) p.getActualTypeArguments()[0]; //com.example.demo.mapper.StudentMapper
        java.lang.Class tClass1 = (java.lang.Class<T>) p.getActualTypeArguments()[1]; //com.example.demo.entity.Student

        List<Object> tClass1s = JSON.parseArray(jsonValue, tClass1);
        tClass1s.stream().map(t -> {
            try {
                ReflectFieldUtil.formatString(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return t;
        }).collect(Collectors.toList());

        return tClass1s;
    }

    /**
     * 测试定义多个环绕
     * @return
     */
//    @Around("aroundService()")
//    public Object aroundTest(){
//        List<Student> students = new ArrayList<>();
//        students.add(new Student(5,"name",22,"男",77));
//        return students;
//    }
}
