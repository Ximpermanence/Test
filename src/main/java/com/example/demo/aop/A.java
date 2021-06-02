package com.example.demo.aop;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:正则测试类
 * @author: chenhao
 * @create: 2021/4/23
 **/

public class A {
    private int a;
    private int b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "A{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    public static void main(String[] args) {
        String createUser = "xxx";

        String pattern = "(\\[(.*?)])";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        java.util.regex.Matcher m = r.matcher(createUser);
        String result = null;
        while (m.find()) {
            result = StringUtils.strip(m.group(1), "[]");
        }
        Map<String,String> map = new HashMap<>();
        map.put("金华市工汇", "浙江工汇网络科技有限公司");
        map.put("嘉兴秀洲区住建", "嘉兴市恒生科技有限公司");
        map.put("人社-台州住建", "浙江标点信息科技有限公司");
        map.put("湖州长兴县人社平台", "湖州孚聪信息科技有限公司");
        map.put("宁波市区县人社海曙", "杭州姜太公信息科技有限公司");
        map.put("衢州江山劳务清", "浙江鸿正软件科技有限公司");
        map.put("嘉兴市住建品茗", "杭州品茗安控信息技术股份有限公司");
        map.put("舟山市人社恩普", "浙江网新恩普软件有限公司");
        map.put("丽水市住建国泰新点", "国泰新点软件股份有限公司");
        map.put("衢州市本级住建方欣", "杭州睿建信息科技有限公司");
        map.put("银行导入", "银行");
        map.put("嘉兴开发区人社", "浙江标点信息科技有限公司");
        map.put("宁波市大榭", "杭州姜太公信息科技有限公司");
        map.put("宁波市高新区", "杭州姜太公信息科技有限公司");
        map.put("宁波市东钱湖", "杭州姜太公信息科技有限公司");
        map.put("宁波市靳城区", "杭州姜太公信息科技有限公司");
        map.put("姜太公", "杭州姜太公信息科技有限公司");
        map.put("宁波市住建(明义)", "宁波明义数字科技有限公司");
        map.put("衢州常山住建", "杭州沃思铠科技有限公司");
        map.put("华卫", "华卫智能科技有限公司");
        map.put("企薪", "国考数据");
        map.put("杭州", "杭州品茗安控信息技术股份有限公司");
        map.put("宁波鲁班长科技", "鲁班长（深圳）科技有限公司");
        map.put("住建", "浙江省住建平台");
        map.put("嘉兴平湖方欣", "杭州睿建信息科技有限公司");
        map.put("嘉兴海盐方欣", "杭州睿建信息科技有限公司");
        map.put("嘉兴联通", "杭州品茗安控信息技术股份有限公司");
        map.put("方欣企业", "杭州睿建信息科技有限公司");
        map.put("衢州地区项目查询", "杭州沃思铠科技有限公司");
        map.put("工汇宁波宁海项目查询", "浙江工汇网络科技有限公司");
        map.put("杭州掌勤", "杭州掌勤信息科技有限公司");
        map.put("凡东", "杭州凡东科技有限公司");
        map.put("新中大", "杭州新中大科技股份有限公司");
        //var frt = list.FirstOrDefault(o=>o.Item1==$"[{}]");

        if(map.containsKey(result)){
            createUser = map.get(result);
        }

        System.out.println(createUser);
    }
}
