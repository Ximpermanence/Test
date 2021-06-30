package com.example.demo.pojo.vo.dataDemo.read;

import java.util.List;

/**
 * @description:
 * @author: chenhao
 * @create:2020/8/25 15:30
 **/

/**
 * 假设这是Dao存储
 */
public class DemoDao {
    public <T> void save(List<T> list) {
    //如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        list.forEach(System.out::println);
    }

}
