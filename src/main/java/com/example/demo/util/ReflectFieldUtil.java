package com.example.demo.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * @description:
 * @author: chenhao
 * @create:2020/9/15 15:47
 **/
public class ReflectFieldUtil {

    /**
     * 判断obejct对应的实体类是否存在fieldName属性
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static Boolean ifClassHasField(String fieldName, Object object) {

        Field[] fields = object.getClass().getDeclaredFields();
        boolean flag = false;
        int a = fields.length;
        for (int i = 0; i <= fields.length; i++) {
            if (Objects.equals(fields[i].getName(), fieldName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 给该对象所有为空的字符串类型字段赋值
     *
     * @param object
     * @return
     */
    public static Object formatString(Object object) throws IllegalAccessException {

        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
//            System.out.println(MessageFormat.format("第 {0} 个field所属类为：{1}", i, fields[i].getType().getName()));
            fields[i].setAccessible(true);
//            if (Objects.equals(fields[i].getType().getName(), "java.lang.String") && Objects.isNull(fields[i].get(object))) {
//                fields[i].set(object, "--");
//            }
            if (Objects.equals(fields[i].getType().getName(), "java.lang.String")) {
                if (StringUtils.isEmpty(fields[i].get(object))) {
                    fields[i].set(object, "--");
                }
            }
            if (Objects.equals(fields[i].getType().getName(), "java.lang.Integer")) {
                if (StringUtils.isEmpty(fields[i].get(object))) {
                    fields[i].set(object, 0);
                }
            }
        }
        return object;
    }

    private static String[] types = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};


    /**
     * 解析实体类，获取实体类中的属性
     *
     * @param obj
     * @return
     */
    public static String getFieldsValue(Object obj) {

        //通过反射获取所有的字段，getFileds()获取public的修饰的字段
        //getDeclaredFields获取private protected public修饰的字段
        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getTypeName();
        for (String t : types) {
            if (t.equals(typeName)) {
                return "";
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Field f : fields) {
            //在反射时能访问私有变量
            f.setAccessible(true);
            try {
                for (String str : types) {
                    //这边会有问题，如果实体类里面继续包含实体类，这边就没法获取。
                    //其实，我们可以通递归的方式去处理实体类包含实体类的问题。
                    if (f.getType().getName().equals(str)) {
                        sb.append(f.getName() + " : " + f.get(obj) + ", ");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
