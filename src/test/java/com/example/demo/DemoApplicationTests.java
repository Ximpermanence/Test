package com.example.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.aop.AppConfig;
import com.example.demo.aop.UserDao;
import com.example.demo.entity.Class;
import com.example.demo.entity.Structure;
import com.example.demo.entity.Student;
import com.example.demo.entity.SysNation;
import com.example.demo.entity.TestJs.dto.GetLabourerDTO;
import com.example.demo.entity.TestJs.res.GetLabourerListPageRes;
import com.example.demo.enums.StudentSortEnum;
import com.example.demo.mapper.ClassMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.ClassService;
import com.example.demo.service.StudentService;
import com.example.demo.util.EncodeUtil;
import com.example.demo.util.EnumUtils;
import com.example.demo.util.ReflectFieldUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

@SpringBootTest
@MapperScan("com.example.demo.test")
@Slf4j
class DemoApplicationTests {

    @Autowired
    private ClassService classService;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 临时原始数据字典（省）
     */
    private static Map<String, String> temp_dict_province = new HashMap<String, String>();

    /**
     * 临时原始数据字典（市）
     */
    private static Map<String, String> temp_dict_city = new HashMap<String, String>();

    /**
     * 临时原始数据字典（区/县）
     */
    private static Map<String, String> temp_dict_district = new HashMap<String, String>();
    private static final int OSHI_WAIT_SECOND = 1000;


    private static void accept(Student a) {
        out.println(a.getGender());
    }

    private static Student apply(Student a) {
        out.println(a.getAge());
        return a;
    }


    @Test
    void contextLoads() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()){
//            if("1".equals(iterator.next())){
//                iterator.remove();
//            }
//        }
        for (String str : list) {
            if ("2".equals(str)) {
                list.remove(str);
            }
        }
        list.forEach(out::println);

    }

    /**
     * 将excel导入数据库
     */
    @Test
    void Test1() {
        // 设定Excel文件所在路径
//        String excelFileName = "C:\\Users\\CH\\Desktop\\table\\classTable.xls";
        String excelFileName = "c:/table/classTable.xls";
        // 读取Excel文件内容
//        List<ClassExcelVO> readResult = ExcelUtil.readExcel(excelFileName);
//        readResult.forEach(System.out::println);

//        List<Map<String,Object>> classList = cn.hutool.poi.excel.ExcelUtil.getReader(excelFileName).readAll();
//        classList.forEach(System.out::println);

        List<List<Object>> classList = ExcelUtil.getReader(excelFileName).read();
        List<Class> resultList = new ArrayList<>();
//        for(List<Object> list: classList){
//
//        }
        for (int i = 1; i < classList.size(); i++) {
            List<Object> objectList = classList.get(i);
            Class aClass = new Class();
            aClass.setName((String) objectList.get(0));
//            aClass.setTid(objectList.get(1) );
            aClass.setTid(Integer.parseInt(String.valueOf(objectList.get(1))));
            resultList.add(aClass);
        }
        classService.saveBatch(resultList);

    }


    /**
     * 可能是使所有的bean使用同名方法，忘记了
     */
    @Test
    void Test2() {
        //spring启动，将对象变成代理对象
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        SpringApplication springApplication = new SpringApplication();
//        springApplication.setBanner();
        UserDao bean = ac.getBean(UserDao.class);
        bean.query();

    }

    /**
     * 数据类型转换相关
     *
     * @throws ParseException
     */
    @Test
    void Test4() throws ParseException {
        Date date = new Date();
        currentTimeMillis();
//        String dateString = DateUtil.format(date,"yyyy-MM-dd HH:mm:ss");
//        Date date1 = DateUtil.parse(dateString);
        String dateString = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        out.println(1);
    }


    /**
     * 获取某数据库中所有表名和表结构
     */
    @Test
    void Test5() {

        List<String> list = classMapper.listTableName();
        Map<String, List<Structure>> map = new HashMap<>();
        list.forEach(t -> {
            List<Structure> classStructure = classMapper.getClassStructure(t);
            map.put(t, classStructure);
        });
        out.println(1);
    }

    /**
     * 默认值
     */
    @Test
    void Test6() {
        SysNation sysNation = new SysNation();
        String a = sysNation.getNation();
        int b = sysNation.getCode();
        out.println(a + b);
    }

    /**
     * List实现方式
     */
    @Test
    void Test7() {
        List<Class> classList = classService.list();
        if (classList instanceof RandomAccess) {

            out.println("内部数组实现");
        } else {
            out.println("内部链表实现");
        }
    }

    /**
     * 测试条件为空是否报错
     */
    @Test
    void Test8() {
        List<Integer> tidList = new ArrayList<>();
        LambdaQueryWrapper<Class> classLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classLambdaQueryWrapper.in(Class::getTid, tidList);
        List<Class> classList = classService.list(classLambdaQueryWrapper);
        out.println(1);
    }

    /**
     * 测试属性反射方法：判断一个类是否存在某一个类型的字段,并通过反射赋值
     */
    @Test
    void Test9() throws NoSuchFieldException, IllegalAccessException {
        Student student = new Student(9, "10", 11, "J", 12);
        List<Student> students = searchList("name", student, "无常");
        out.println(students.get(0).toString());
    }


    /**
     * List泛型的使用以及使用反射给泛型类的指定字段赋值
     *
     * @param field
     * @param t
     * @param saveMessage
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public <T> List<T> searchList(String field, T t, String saveMessage) throws NoSuchFieldException, IllegalAccessException {
        Boolean ifExist = ReflectFieldUtil.ifClassHasField(field, t);

        List<T> personList = new ArrayList<>();
        T person = t;
        if (ifExist) {
            Field field1 = t.getClass().getDeclaredField(field);
            field1.setAccessible(true);
            field1.set(t, saveMessage);
        }
        personList.add(person);
        return personList;
    }

    /**
     * List泛型的使用以及使用反射给泛型类的指定字段赋值
     *
     * @param field
     * @param object
     * @param saveMessage
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public <T> List<T> searchList2(String field, Object object, String saveMessage) throws NoSuchFieldException, IllegalAccessException {
        Boolean ifExist = ReflectFieldUtil.ifClassHasField(field, object);

        List<T> personList = new ArrayList<>();
        T person = (T) object;
        if (ifExist) {
            Field field1 = object.getClass().getDeclaredField(field);
            field1.setAccessible(true);
            field1.set(object, saveMessage);
        }

        personList.add(person);
        return personList;
    }

    /**
     * 测试属性反射方法：使某个类的所有String类型字段如果为空就赋值为"--"
     *
     * @throws IllegalAccessException
     */
    @Test
    void Test10() throws IllegalAccessException {
        Student student = new Student(106, "张四五", 19, "男", 28);
        student.setName(null);
        student.setGender("");
        ReflectFieldUtil.formatString(student);
        out.println(student.toString());
    }

    /**
     * 测试aop赋值是否实现
     */
    @Test
    void Test11() {
        List<Student> students = studentService.list();
//        List<Class> classList = classService.list();
        out.println(1);
    }

    /**
     * mybatis自定义sql编写
     */
    @Test
    void Test12() {
        teacherMapper.getSameSexTeacher("男").forEach(out::println);
    }

    /**
     * map新方法
     */
    @Test
    void Test13() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("a", "A");
        stringMap.put("b", "B");
        String value = stringMap.getOrDefault("d", "如果没有该key，返回默认值");
        stringMap.forEach((k, v) -> {
            out.println(k + "=" + v);
            stringMap.put(k, k + v);
        });
        stringMap.forEach((k, v) -> out.println(k + "=" + v));
        stringMap.putIfAbsent("c", "C");
        stringMap.replaceAll((k, v) -> v.toLowerCase());
        out.println(value);
    }

    /**
     * stream.sort()
     */
    @Test
    void Test14() {
//        studentService.list().stream().sorted(Comparator.comparing(Student::getAge)).forEach(System.out::println);
        studentService.list().stream().sorted((o1, o2) -> o2.getAge().compareTo(o1.getAge())).forEach(out::println);
    }

    /**
     * 改变日期格式
     */
    @Test
    void Test15() {
        String initTime = "2019-8-5";
        Date afterDate = DateUtil.parse(initTime, "yyyy-MM-dd");
        String afterFormat = DateUtil.format(afterDate, "yyyy-MM-dd");
        out.println(1);

    }


    /**
     * 获取指定某年,某月的天数
     */
    @Test
    void Test16() {
        int monthLength = LocalDate.of(2021, 2, 5).lengthOfMonth();
        int yearLength = LocalDate.of(2020, 5, 2).lengthOfYear();
        LocalDate date = LocalDate.ofYearDay(2020, 2);
        out.println(yearLength);
    }


    /**
     * 获取所有建筑工人
     */
    @Test
    void Test17() {
        String url = "http://121.40.57.159:6002/api/v1/CompanyLabourer/GetLabourerListPage?name=&cardNo=&mobile=&worktypeNo=&companyName=&projectName=&isJob=&provinceCode=&cityCode=&countyCode=&province_code=&city_code=&county_code=&industryType=0&PageIndex=1&PageTotal=0&PageSize=1000";
        String result1 = HttpRequest.get(url)
                .header("X-Token", "b109001c-a507-404e-9770-a1e969acf0d8")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        out.println(result1);
        String url2 = "http://121.40.57.159:6003/person";
//       String result2 = HttpUtil.get(url);
        String result2 = HttpRequest.get(url)
                .header("X-Token", "b109001c-a507-404e-9770-a1e969acf0d8")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        out.println(result2);
    }

    /**
     * 获取json串
     */
    @Test
    void Test18() {
        Class clas1 = new Class(1, "一班非一", 1);
        Class clas2 = new Class(2, "二班非一", 2);
        List<Class> classList = new ArrayList<>();
        classList.add(clas1);
        classList.add(clas2);
        String JsonString = JSON.toJSONString(classList);
        out.println(1);
    }

    /**
     * 测试嘉善接口
     */
    @Test
    void Test19() {


//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("userName", "superadmin");
//        paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
//        String post = HttpUtil.post("http://101.37.66.65:6013/api/v1/User/login",paramMap);
        String token = "f9b6f220-d303-4ece-9c87-20d4579d1b71";

        List<String> lidList = new ArrayList<>();

//        测建筑工人分页接口——√
        for (int i = 1; i <= 30; i++) {
//            String result = HttpUtil.get("http://localhost:8080/api/v1/CompanyLabourer/GetLabourerListPage?name=&cardNo=&mobile=&worktypeNo=&companyName=&projectName=&isJob=&provinceCode=&cityCode=&countyCode=&province_code=&city_code=&county_code=&industryType=0&PageIndex=" + i + "&PageTotal=585&PageSize=20&X-Token="+token);
            String result = HttpUtil.get("http://101.37.66.65:6012/api/v1/CompanyLabourer/GetLabourerListPage?name=&cardNo=&mobile=&worktypeNo=&companyName=&projectName=&isJob=&provinceCode=&cityCode=&countyCode=&province_code=&city_code=&county_code=&industryType=0&PageIndex=" + i + "&PageTotal=586&PageSize=20&X-Token=" + token);
            GetLabourerListPageRes getLabourerListPageRes = JSON.parseObject(result, GetLabourerListPageRes.class);
            if (!getLabourerListPageRes.getIsSuccess()) {
                out.println(i);
            }
            ;
            String resultData = getLabourerListPageRes.getData();
            List<GetLabourerDTO> getLabourerDTOS = JSON.parseArray(resultData, GetLabourerDTO.class);
            getLabourerDTOS.stream().map(GetLabourerDTO::getLidF).peek(lidList::add).collect(Collectors.toList());

        }
        final int[] i = {0};
        lidList.forEach(t -> {

            //个人信息
//            String labourerInfo = HttpUtil.get("http://localhost:8080/api/v1/CompanyLabourer/GetLabourerInfo?Parameter="+t+"&X-Token="+token);
            String labourerInfo = HttpUtil.get("http://101.37.66.65:6012/api/v1/CompanyLabourer/GetLabourerInfo?Parameter=" + t + "&X-Token=" + token);
            GetLabourerListPageRes getLabourerListPageRes1 = JSON.parseObject(labourerInfo, GetLabourerListPageRes.class);
            if (!getLabourerListPageRes1.getIsSuccess()) {
                log.error("{}号个人信息出错,lid为{}", i[0], getLabourerListPageRes1.getData());
                out.println(i[0] + "个人信息出错" + getLabourerListPageRes1.getData());
            }


            //个人履历
//            String labourerResume = HttpUtil.get("http://localhost:8080/api/v1/CompanyLabourer/GetLabourerResumeList?Parameter="+t+"&PageIndex=1&PageTotal=0&PageSize=20"+"&X-Token="+token);

            String labourerResume = HttpUtil.get("http://101.37.66.65:6012/api/v1/CompanyLabourer/GetLabourerResumeList?Parameter=" + t + "&PageIndex=1&PageTotal=0&PageSize=20" + "&X-Token=" + token);
            GetLabourerListPageRes getLabourerListPageRes2 = JSON.parseObject(labourerResume, GetLabourerListPageRes.class);
            if (!getLabourerListPageRes2.getIsSuccess()) {
                log.error("{}号个人信息出错,lid为{}", i[0], getLabourerListPageRes1.getData());
                out.println(i[0] + "个人履历出错" + getLabourerListPageRes1.getData());
            }
            i[0]++;
            out.println(i[0] + "号表示没有问题");
            if (i[0] % 10 == 0) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    out.println("第" + i + "此抛出了异常");
                    e.printStackTrace();
                }
            }

        });

        out.println(1);
    }

    /**
     * 对list去重
     */
    @Test
    void Test20() {

        List<Student> students = new LinkedList<>();
        students.add(new Student(1, "一", 1, "男", 1));
        students.add(new Student(2, "二", 2, "男", 2));
        students.add(new Student(3, "三", 3, "女", 2));
        students.add(new Student(4, "四", 4, "女", 1));

        //去重
        List<Student> students2 = students.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(Student::getGender))
                        ), ArrayList::new
                )
        );
        out.println(students.iterator());
    }


    /**
     * 测试企薪人员查询
     */
    @Test
    void Test21() {


//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("userName", "superadmin");
//        paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
//        String post = HttpUtil.post("http://101.37.66.65:6013/api/v1/User/login",paramMap);
        String token = "63d2eeea-a258-4083-80ba-4dbed8880213";

        List<String> lidList = new ArrayList<>();

//        测建筑工人分页接口——先查个50页吧
        for (int i = 11; i <= 50; i++) {
            String result = HttpUtil.get("http://121.40.57.159:8007/api/v1/CompanyLabourer/GetLabourerListPage?name=&cardNo=&mobile=&worktypeNo=&companyName=&projectName=&isJob=&provinceCode=330000&cityCode=&countyCode=&province_code=330000&city_code=&county_code=&PageIndex=" + i + "&PageTotal=3053717&PageSize=20&X-Token=" + token);

            GetLabourerListPageRes getLabourerListPageRes = JSON.parseObject(result, GetLabourerListPageRes.class);
            if (!getLabourerListPageRes.getIsSuccess()) {
                log.error("【第{}页】请求失败", i);
//                System.out.println(i);
            }
            ;
            String resultData = getLabourerListPageRes.getData();
            List<GetLabourerDTO> getLabourerDTOS = JSON.parseArray(resultData, GetLabourerDTO.class);
            getLabourerDTOS.stream().map(GetLabourerDTO::getLidF).peek(lidList::add).collect(Collectors.toList());

        }
        final int[] i = {0};
        lidList.forEach(t -> {

            //个人信息

            String labourerInfo = HttpUtil.get("http://121.40.57.159:8007/api/v1/CompanyLabourer/GetLabourerInfo?Parameter=" + t + "&X-Token=" + token);
            GetLabourerListPageRes getLabourerListPageRes1 = JSON.parseObject(labourerInfo, GetLabourerListPageRes.class);
            if (!getLabourerListPageRes1.getIsSuccess()) {
                log.error("【{}号个人信息出错,lid为{},姓名为】", i[0], getLabourerListPageRes1.getData());
                out.println(i[0] + "个人信息出错" + getLabourerListPageRes1.getData());
            }


            //个人履历

            String labourerResume = HttpUtil.get("http://121.40.57.159:8007/api/v1/CompanyLabourer/GetLabourerResumeList?Parameter=" + t + "&PageIndex=1&PageTotal=0&PageSize=20 " + "&X-Token=" + token);

            GetLabourerListPageRes getLabourerListPageRes2 = JSON.parseObject(labourerResume, GetLabourerListPageRes.class);
            if (!getLabourerListPageRes2.getIsSuccess()) {
                log.error("【{}号个人信息出错,lid为{}】", i[0], getLabourerListPageRes1.getData());
                out.println(i[0] + "个人履历出错" + getLabourerListPageRes1.getData());
            }

            //银行信息
            String labourerBankInfo = HttpUtil.get("http://121.40.57.159:8007/api/v1/LabourerBlack/GetLabourerBankList?Parameter=" + t + "&X-Token=" + token);
            GetLabourerListPageRes getLabourerBankListPageRes1 = JSON.parseObject(labourerBankInfo, GetLabourerListPageRes.class);
            if (!getLabourerBankListPageRes1.getIsSuccess()) {
                log.error("【{}号银行信息,lid为{}】", i[0], getLabourerListPageRes1.getData());
                out.println(i[0] + "银行信息出错" + getLabourerListPageRes1.getData());
            }

            i[0]++;
//            System.out.println(i[0] + "号表示没有问题");
            if (i[0] % 10 == 0) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
//                    System.out.println("第" + i + "此抛出了异常");
                    log.error("第{}次抛出了异常", i);
                    e.printStackTrace();
                }
            }

        });

        out.println(1);

    }


    /**
     * StringBuffer去除最后一个元素
     */
    @Test
    void Test22() {
        String a = "16,18,";
        StringBuffer sba = new StringBuffer(a);
        sba.deleteCharAt(sba.length() - 1);
        out.println(sba.toString());
    }

    /**
     * 测试encode中Unicode和中文的转换
     */
    @Test
    void Test23() {
        String str = "木";
        String s = EncodeUtil.stringToUnicode(str);
        out.println(s);  //Ox6728
        String str2 = EncodeUtil.unicodeToString(s);
        out.println(str2);
    }

    /**
     * 测试空的list能不能用stream.map
     */
    @Test
    void Test24() {
        List<Student> students = new ArrayList<>();
//        students.add(new Student(1,"ch",22,"男",1));
        students.stream().map(DemoApplicationTests::apply);
    }

    /**
     * random的stream输出
     */
    @Test
    void Test25() {
        Random random = new Random();
        random.ints().limit(10).forEach(out::println);
    }

    /**
     * BigDecimal除法
     */
    @Test
    void Test26() {
        BigDecimal a = new BigDecimal(5488);
        out.println(a.divide(new BigDecimal(10000), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    }

    /**
     * 查询结果为null看下stream是否会报错-不会
     */
    @Test
    void Test27() {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getName, "sdfdsfsdf");
        List<String> studentName = studentService.list().stream().map(Student::getName).distinct().collect(Collectors.toList());
        out.println(1);
    }

    /**
     * 用枚举来进行复杂排序
     */
    @Test
    void Test28() {
        List<Student> students = studentService.list();
        List<Student> students1 = students.stream()
                .sorted(Comparator.comparing(t -> {
                    try {
                        return Integer.valueOf(EnumUtils.getValueByLabel(StudentSortEnum.class, t.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }))
                .collect(Collectors.toList());

        out.println(1);

    }

    /**
     * 获取枚举类key的list
     */
    @Test
    void Test29() {
        StudentSortEnum[] enumNames = StudentSortEnum.values();
        List<String> names = EnumUtil.getNames(StudentSortEnum.class);

        List<String> labels = new ArrayList<>();
        List<String> values = new ArrayList<>();
        try {
            labels = EnumUtils.getEnumLabels(StudentSortEnum.class);
            values = EnumUtils.getEnumValues(StudentSortEnum.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println(1);
    }

    /**
     * 查看common lang3 和spring的collectionUtils和stringUtils
     */
    @Test
    void Test30() {
        boolean a = StringUtils.isEmpty("a");
        boolean b = org.springframework.util.StringUtils.isEmpty("b");

    }

    /**
     * 将一个txt的文件拿出来放到一个List里
     */
    @Test
    void Test31() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("GB2260-2015.txt");
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("GB2260-2015.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Map<String, String> addressMap = new HashMap<>(3600);
        int i = 0;
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            String[] split = null;
            if (line.contains(",")) {
                split = line.split(",");
            } else if (line.contains("\t")) {
                split = line.split("\t");
            }
            String name = split[0].trim();
            String code = split[1].trim();
            addressMap.put(code, name);
//            if (line.contains("\t")) {
//                i++;
//                String[] split = line.split("\t");
//                String code = split[0].trim();
//                String name = split[1].trim();
//                addressMap.put(code, name);
//
//                // 省
//                if (code.endsWith("0000")) {
//                    temp_dict_province.put(code, name);
//                } else if (code.endsWith("00") && !code.endsWith("0000")) {
//                    temp_dict_city.put(code, name);
//                } else {
//                    temp_dict_district.put(code, name);
//                }
//            }
        }
        out.println(1);
    }


    /**
     * jdk8 stream读取文件
     */
    @Test
    public void Test32() {
        Path filePath = Paths.get("C:\\Users\\CH\\IdeaProjects\\space\\Test\\src\\main\\resources\\GB2260-2015.txt");

        List<String> filteredLines = new ArrayList<>();
        final int[] a = {0};
        final int[] b = {0};
        final int[] c = {0};
        try (Stream<String> lines = Files.lines(filePath)) {

            filteredLines = lines
                    .filter(s -> s.contains("\t"))
                    .peek(t -> {
                        String[] split = t.split("\t");
                        String code = split[0];
                        String name = split[1];
                        if (code.endsWith("0000")) {
                            a[0]++;
                            temp_dict_province.put(code, name);
                        } else if (code.endsWith("00")) {
                            b[0]++;
                            temp_dict_city.put(code, name);
                        } else {
                            c[0]++;
                            temp_dict_district.put(code, name);
                        }

                    })
                    .collect(Collectors.toList());

            filteredLines.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();//只是测试用例，生产环境下不要这样做异常处理
        }
        out.println(1);
    }

    /**
     * 打开指定的文件
     */
    @Test
    void Test33() throws IOException {
        String[] sh = {"cmd", "/c", "C:/Users/CH/IdeaProjects/space/JDPruchase/QCode.png"};
        String sh1 = "cmd /c C:/Users/CH/IdeaProjects/space/JDPruchase/QCode.png";
        Runtime.getRuntime().exec(sh);
    }

    /**
     * 随机数
     */
    @Test
    void Test34() {
        int a = (int) ((Math.random() * (9999999 - 1000000 + 1)) + 1000000);
        out.println(a);
    }

    /**
     * 加密
     */
    @Test
    void Test35() throws InterruptedException {
        out.println(SecureUtil.md5("这是一个测试文案"));
        out.println(SecureUtil.md5("123456789"));

    }

    /**
     * float会丢失精度 保留两位小数
     */
    @Test
    void Test36() {

        float float1 = 0.55f;
        float float2 = 0.1f;
        float floatSum = float1 + float2;
        out.println(floatSum);
        //保留两位
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");
        float floatSum2 = Float.valueOf(decimalFormat.format(floatSum));
        out.println(floatSum2);
        //加百分号
//        DecimalFormat decimalFormat2 = new DecimalFormat("##0.00%");
        DecimalFormat decimalFormat2 = new DecimalFormat("##%");
        String floatSum3 = decimalFormat2.format(floatSum);
        out.println(floatSum3);

        //加千位符
        DecimalFormat decimalFormat3 = new DecimalFormat("#,###");
        Long longNum = 651151561L;
        String longFormat = decimalFormat3.format(651151561L);
        out.println(longFormat);
    }

    /**
     * 通过加@Accessors(chain = true)使得连续set
     */
    @Test
    void Test37() {
        Class cas = new Class();
        cas.setName("sa").setTid(1).setId(1);
    }

    /**
     * hutool爬虫
     */
    @Test
    void Test38() {
        //请求列表页
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        //使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }
    }

    /**
     * 尝试todo
     */
    @Test
    void Test39() {
        //TODO: 2021/2/1 测试todo  @陈浩
//        String a = StringUtils.toRootUpperCase("device_is_online");
//        out.println(1);
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        System.out.println(list.subList(1, 4));
    }

    /**
     * 获取服务器信息
     */
    @Test
    void Test40() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        GlobalMemory memory = hal.getMemory();
        OperatingSystem operatingSystem = si.getOperatingSystem();

        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        //内存
        memory.getTotal();
        long a = memory.getTotal() - memory.getAvailable();
        memory.getAvailable();

        //jvm
        Properties props = System.getProperties();
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().maxMemory();
        Runtime.getRuntime().freeMemory();
        props.getProperty("java.version");
        props.getProperty("java.home");

        //磁盘信息
        FileSystem fileSystem = operatingSystem.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            fs.getMount();
            fs.getType();
            fs.getName();
            long usedProportion = used / total;

        }

    }

    /**
     *
     */
    @Test
    void Test41(){

    }
}
