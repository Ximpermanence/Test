package com.example.demo.mapper;

import com.example.demo.entity.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Structure;
import com.example.demo.pojo.vo.ClassStudentVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 班级表 Mapper 接口
 * </p>
 *
 * @author ch
 * @since 2020-08-17
 */
public interface ClassMapper extends BaseMapper<Class> {




//    @Select("desc class")
    @Select("select DISTINCT COLUMN_NAME,DATA_TYPE from information_schema.COLUMNS where table_name = #{dataTable}")
//    @Select("SELECT\n" +
//            "t.COLUMN_NAME AS fieldName,\n" +
//            "(\n" +
//            "CASE\n" +
//            "WHEN t.IS_NULLABLE = 'YES' THEN\n" +
//            "'true'\n" +
//            "ELSE\n" +
//            "'false'\n" +
//            "END\n" +
//            ") AS blank,\n" +
//            "(t.ORDINAL_POSITION * 10) AS sort,\n" +
//            "t.COLUMN_COMMENT AS fieldComment,\n" +
//            "t.COLUMN_TYPE AS fieldType\n" +
//            "FROM\n" +
//            "information_schema.`COLUMNS` t\n" +
//            "WHERE\n" +
//            "t.TABLE_SCHEMA = (SELECT DATABASE())\n" +
//            "AND t.TABLE_NAME = 'class';")
    List<Structure> getClassStructure(String tableName);

    @Select("select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<String> listTableName();

    /**
     * 获取班级以及对应的学生
     * @param
     * @return
     */
    List<ClassStudentVO> listClassStudent();
}
