package com.example.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.aop.AppConfig;
import com.example.demo.aop.UserDao;
import com.example.demo.entity.Class;
import com.example.demo.entity.*;
import com.example.demo.enums.StudentSortEnum;
import com.example.demo.mapper.ClassMapper;
import com.example.demo.mapper.TeacherMapper;
import com.example.demo.service.ClassService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
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

import java.io.*;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    private static String testString;

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
        list.removeIf("2"::equals);
        list.forEach(out::println);

    }

    /**
     * hutool excel导入获取
     */
    @Test
    void Test1() {
        // 设定Excel文件所在路径
//        String excelFileName = "C:\\Users\\CH\\Desktop\\table\\classTable.xls";
        String excelFileName = "c:/table/classTable.xls";
        // 读取Excel文件内容

//        List<Map<String,Object>> classList = ExcelUtil.getReader(excelFileName).readAll();
//        classList.forEach(System.out::println);

        List<List<Object>> classList = ExcelUtil.getReader(excelFileName).read();
        List<Class> resultList = new ArrayList<>();

        for (int i = 1; i < classList.size(); i++) {
            List<Object> objectList = classList.get(i);
            Class aClass = new Class();
            aClass.setName((String) objectList.get(0));
            aClass.setTid((Integer) objectList.get(1));
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

    //    stream分组
    @Test
    void Test3() {

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
        List<Student> students = searchFieldInitValueInList("name", student, "无常");
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
    public <T> List<T> searchFieldInitValueInList(String field, T t, String saveMessage) throws NoSuchFieldException, IllegalAccessException {
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
    public <T> List<T> searchFieldInitValueInList2(String field, Object object, String saveMessage) throws NoSuchFieldException, IllegalAccessException {
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
     *
     */
    @Test
    void Test17() {
        boolean a = true;
        boolean b = false;
        short z = 40;
        if (z++ == 40 && (b = true)) {
            z++;
        }
        if (a = false || ++z == 43) {
            z++;
        }
        out.println(z);

        //==：比较的是内存中的地址是否相同;equals()该方法根据不同的类的实现方式，重写父类Object方法的话就按照自己的定义来，没有重写的话就是跟==相同(@Data重写了equals方法？)
        TeacherClass teacherClass = new TeacherClass(1, 1, 1);
        TeacherClass teacherClass1 = new TeacherClass(1, 1, 1);
        TeacherClass teacherClass2 = teacherClass;
        boolean c = teacherClass == teacherClass1;
        boolean d = teacherClass == teacherClass2;
        boolean c1 = teacherClass.equals(teacherClass1);
        boolean d1 = teacherClass.equals(teacherClass2);

        out.println("" + c + d + c1 + d1);
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
     * 对list去重
     */
    @Test
    void Test19() {

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
     * StringBuffer去除最后一个元素
     */
    @Test
    void Test20() {
        String a = "16,18,";
        StringBuffer sba = new StringBuffer(a);
        sba.deleteCharAt(sba.length() - 1);
        out.println(sba.toString());
    }

    /**
     * 测试encode中Unicode和中文的转换(用hutool即可)
     */
    @Test
    void Test21() {
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
    void Test22() {
        List<Student> students = new ArrayList<>();
//        students.add(new Student(1,"ch",22,"男",1));
        students.stream().map(DemoApplicationTests::apply);
    }

    /**
     * random的stream输出
     */
    @Test
    void Test23() {
        Random random = new Random();
        random.ints().limit(10).forEach(out::println);
    }

    /**
     * BigDecimal除法
     */
    @Test
    void Test24() {
        BigDecimal a = new BigDecimal(5488);
        out.println(a.divide(new BigDecimal(10000), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
    }

    /**
     * 查询结果为null看下stream是否会报错-不会
     */
    @Test
    void Test25() {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getName, "sdfdsfsdf");
        List<String> studentName = studentService.list().stream().map(Student::getName).distinct().collect(Collectors.toList());
        out.println(1);
    }

    /**
     * 用枚举来进行复杂排序
     */
    @Test
    void Test26() {
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
    void Test27() {
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
    void Test28() {
        boolean a = StringUtils.isEmpty("a");
        boolean b = org.springframework.util.StringUtils.isEmpty("b");

    }

    /**
     * 将一个txt的文件拿出来放到一个List里
     */
    @Test
    void Test29() throws IOException {
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
    public void Test30() {
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
    void Test31() throws IOException {
        String[] sh = {"cmd", "/c", "C:/Users/CH/IdeaProjects/space/JDPruchase/QCode.png"};
        String sh1 = "cmd /c C:/Users/CH/IdeaProjects/space/JDPruchase/QCode.png";
        Runtime.getRuntime().exec(sh);
    }

    /**
     * 随机数和case合并
     */
    @Test
    void Test32() {
        //1-10  [1,10)
//        int a = (int) ((Math.random() * 9) + 1);
        //1-10  [1,10]
        int a = (int) ((Math.random() * 10-1+1) + 1);
        switch (a) {
            case 1:
            case 2:
            case 3:
                out.println(a);
                break;
            case 4:
            case 5:
            case 6:
                out.println(a * 10);
                break;
            default:
                out.println(a * 100);
                break;
        }
        out.println(a);
    }

    /**
     * 加密
     */
    @Test
    void Test33() throws InterruptedException {
        out.println(SecureUtil.md5("这是一个测试文案"));
        out.println(SecureUtil.md5("123456789"));

    }

    /**
     * float会丢失精度 保留两位小数
     */
    @Test
    void Test34() {

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
    void Test35() {
        Class cas = new Class();
        cas.setName("sa").setTid(1).setId(1);
    }

    /**
     * hutool爬虫
     */
    @Test
    void Test36() {
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
     * 测试todo和list的循环
     */
    @Test
    void Test37() {
        //TODO: 2021/2/1 测试todo  @陈浩
//        String a = StringUtils.toRootUpperCase("device_is_online");
//        out.println(1);
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        System.out.println(list.subList(1, 4));
//        list.forEach(t -> {
//            if (t.equals("c")) {
//                out.println("结束吧");
////               break;//使用lambda表达式的foreach无法用break和continue 只能使用return跳过当次循环
//                return;
//            }
//            out.println(t);
//        });
        for (String s : list) {
            if (s.equals("c")) {
                out.println("结束吧");
//                continue;
//                break;
            return;
            }
            out.println(s);
        }
    }

    /**
     * 获取服务器信息
     */
    @Test
    void Test38() {
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
     * 将base64字符串和图片的转换
     */
    @Test
    void Test39() throws IOException {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {

//将图片转为字符串
            Base64.Encoder encoder = Base64.getEncoder();
            inputStream = new FileInputStream("C:\\Users\\CH\\AppData\\Roaming\\Typora\\typora-user-images\\image-20200827163736442.png");
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            String base64Str = encoder.encodeToString(bytes);
            System.out.println(base64Str);

            //将base64字符串转成图片
            //因为idea编译的时候不能定义这么长的字符串，只能先把这个字符串先赋值再写出去
            testString = base64Str;
            Base64.Decoder decoder = Base64.getDecoder();
            testString.replaceAll("\r\n", "");

            byte[] bytes2 = decoder.decode(testString);
            outputStream = new FileOutputStream("d://temp.jpg");
            outputStream.write(bytes2);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }

    }

    /**
     * 两个线程同时执行并得到结果
     */
    @Test
    void Test40() throws ExecutionException, InterruptedException {
        long l1 = currentTimeMillis();
//        nMillionSout(2,"线程1");
//        nMillionSout(2,"线程2");

        CompletableFuture<Long> thread1 = CompletableFuture.supplyAsync(() -> nMillionSout(1, "线程1"));
        CompletableFuture<Long> thread2 = CompletableFuture.supplyAsync(() -> nMillionSout(1, "线程2"));

        CompletableFuture.allOf(thread1, thread2).join();

        thread1.get();
        thread2.get();
        long l2 = currentTimeMillis() - l1;
        out.println("总耗时:" + l2);
    }

    private long nMillionSout(int n, String threadName) {
        long l1 = currentTimeMillis();
        for (int i = 0; i <= n * 1000000; i++) {
            out.println(i + threadName);
        }
        long l2 = currentTimeMillis();
        long subtraction = l2 - l1;
        out.println(threadName + "耗时:" + subtraction);
        return subtraction;
    }

    /**
     * mybatisplus批量修改
     */
    @Test
    void Test41() {
        List<Student> students = studentService.list().stream().collect(Collectors.toList());
        students.stream().map(t -> {
            if (t.getSid() % 4 == 0) {
                t.setName("D" + t.getSid());
            }
            if (t.getSid() % 4 == 3) {
                t.setName("C" + t.getSid());
            }
            if (t.getSid() % 4 == 2) {
                t.setName("B" + t.getSid());
            }
            if (t.getSid() % 4 == 1) {
                t.setName("A" + t.getSid());
            }
            return t;
        }).collect(Collectors.toList());
        studentService.updateBatchById(students);
    }

    /**
     * 给出Date为当前时间的前两个月加1天
     */
    @Test
    void Test42() {

        Date today = new Date();
//        today =  com.example.demo.util.DateUtil.AddMonths(today,-2);
//        today = com.example.demo.util.DateUtil.AddDays(today,1);
        today = DateUtil.offsetMonth(today, -2);
        today = DateUtil.offsetDay(today, 1);
        out.println(today);

    }

    @Autowired
    private TeacherService teacherService;

    /**
     * 测试如何在启动时将数据加载到内存里
     */
    @Test
    void Test43() {
        for (int i = 20; i <= 30; i++) {
            String teacherName = teacherService.getTeacherNameByAge(i);
            out.println(teacherName);
        }
    }

    /**
     * 测试各种valueofnull是否会报错
     */
    @Test
    void Test44() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
        String next = scanner.next();

        Integer s = Integer.valueOf(null);
        String a = String.valueOf(null);
    }
    
    @Test
    void Test45(){

        //指定循环
        circulation: for (int i = 1; i <=4 ; i++) {
            for (int j = 1; j <= 10; j++) {

                if(j%4==0){
//                    break;//默认跳出包裹此路径的最近的一层循环
                break circulation;//结束指定标识的一层循环
                }
                out.print(j);
            }
            out.println();
        }
    }

    @Test
    void Test46(){
        int[] array = new int[]{1,2,3,4,5};
        Teacher a = new Teacher(1,"1",1,"男");
        Teacher b = a;
        b.setGender("女");
        out.println(b);
        out.println(a);
    }

    @Test
    void Test47(){
    Teacher teacher = null;
    teacher.setGender("男");
        out.println(teacher.toString());
    }

    /**
     * 从键盘输入
     *
     * @param args
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int nextInt = scanner.nextInt();
        String scanString = scanner.next();

        out.println(nextInt);
    }

}
