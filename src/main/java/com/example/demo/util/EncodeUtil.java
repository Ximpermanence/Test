package com.example.demo.util;

import cn.hutool.core.text.UnicodeUtil;

/**
 * @description: 转码工具, 直接用hutool的 UnicodeUtil更好
 * @author: chenhao
 * @create:2020/11/10 13:43
 **/
public class EncodeUtil {


    /**
     * Unicode转 汉字字符串
     *
     * @param str \u6728
     * @return '木' 26408
     */
    public static String unicodeToString(String str) {

//        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
//        Matcher matcher = pattern.matcher(str);
//        char ch;
//        while (matcher.find()) {
//            //group 6728
//            String group = matcher.group(2);
//            //ch:'木' 26408
//            ch = (char) Integer.parseInt(group, 16);
//            //group1 \u6728
//            String group1 = matcher.group(1);
//            str = str.replace(group1, ch + "");
//        }
        str = UnicodeUtil.toString(str);
        return str;
    }


    /**
     * 获取字符串的unicode编码
     * 汉字“木”的Unicode 码点为Ox6728
     *
     * @param s 木
     * @return \ufeff\u6728  \ufeff控制字符 用来表示「字节次序标记（Byte Order Mark）」不占用宽度
     */
    public static String stringToUnicode(String s) {
//        try {
//            StringBuffer out = new StringBuffer("");
//            //直接获取字符串的unicode二进制
//            byte[] bytes = s.getBytes("unicode");
//            //然后将其byte转换成对应的16进制表示即可
//            for (int i = 0; i < bytes.length - 1; i += 2) {
//                out.append("\\u");
//                String str = Integer.toHexString(bytes[i + 1] & 0xff);
//                for (int j = str.length(); j < 2; j++) {
//                    out.append("0");
//                }
//                String str1 = Integer.toHexString(bytes[i] & 0xff);
//                out.append(str1);
//                out.append(str);
//            }
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return null;
//        }
        String s1 = UnicodeUtil.toUnicode(s);
        return s1;
    }

    public static void main(String[] args) {
        String s1 = unicodeToString("\u6728");
        String s = stringToUnicode("木");

        System.out.println("String: " + s1 + "\n的unicode值为:\t" + s);

    }
}
