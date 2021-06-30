package com.example.demo.pojo.param;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: chenhao
 * @create: 2021/6/30
 **/

public  class BaseImportParam {
    private final BaseImportParam subClass = getSubClass();

    private BaseImportParam getSubClass() {
        return this;
    }

    /**
     * 获取子类所有属性名，若需排序则需要重写该方法 例如 return "属性名a,属性名b";
     * @return
     */
    public  String getAll() {

        Class clazz = this.subClass.getClass();
        StringBuilder sb = new StringBuilder();
        do {
            //获取包括父类中的属性
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.getName().equals("subClass")) {
                    continue;
                }
                System.out.println(f.getName());
                sb.append(f.getName()).append(",");
            }
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return String.valueOf(sb);
    }
}
