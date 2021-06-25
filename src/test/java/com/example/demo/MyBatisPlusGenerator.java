package com.example.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description: mybatisplus自动生成
 * @author: chenhao
 * @create:2020/8/17 10:40
 **/
@SpringBootTest
public class MyBatisPlusGenerator {

    @Test
    void myGenerator() {
        AutoGenerator generator = new AutoGenerator();

        //设置数据源
        DataSourceConfig dc = new DataSourceConfig();
        dc.setDriverName("com.mysql.jdbc.Driver");
        dc.setUsername("root");
        dc.setPassword("");
        dc.setUrl("jdbc:mysql://localhost:3306/textjava?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true");
        dc.setDbType(DbType.MYSQL);
        generator.setDataSource(dc);

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setSwagger2(true);
        gc.setAuthor("ch");
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setIdType(IdType.AUTO);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setServiceName("%sService");
        generator.setGlobalConfig(gc);

        //配置包
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.example.demo");
        pc.setEntity("entity");
        pc.setController("controller");
        pc.setXml("mapper.xml");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        generator.setPackageInfo(pc);


        //策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setRestControllerStyle(true);
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setVersionFieldName("version");
        generator.setStrategy(sc);
        generator.execute();
    }
}
