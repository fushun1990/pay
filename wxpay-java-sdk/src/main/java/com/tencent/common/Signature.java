package com.tencent.common;

import com.tencent.protocol.base_protocol.BaseReqData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 15:23
 */
public class Signature {


    private static Map<String, List<Field>> fieldMap = new HashMap<String, List<Field>>();

    /**
     * 获取对象的属性字段
     *
     * @param cls
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    private static Field[] getFieds(Class<?> cls) {
        String key = cls.getName();
        Field[] fields = null;
        if (fieldMap.get(key) != null) {
            List<Field> list = fieldMap.get(key);
            fields = list.toArray(new Field[list.size()]);
        } else {
            fields = cls.getDeclaredFields();
            List<Field> fieldList = new ArrayList<Field>();
            for (Field field : fields) {
                fieldList.add(field);
            }
            fieldMap.put(key, fieldList);
        }
        return fields;
    }

    /**
     * 获取 对象的值
     *
     * @param map
     * @param fields
     * @param obj2
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    private static void getObjectFieldValueMap(Map<String, Object> map, Field[] fields, Object obj2) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj = field.get(obj2);
            if (obj != null) {
                String name = field.getName();
                if (name == "configure") {
                    continue;
                }
                if (name.lastIndexOf("_") == name.length() - 1) {
                    name = name.substring(0, name.length() - 1);
                }
                map.put(name, obj);
            }
        }
    }

    /**
     * 获取对象的属性字段，保持到HashMap中
     *
     * @param cls
     * @param obj
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月4日
     * @records <p>  fushun 2017年1月4日</p>
     */
    public static <T> Map<String, Object> getSignMap(Class<T> cls, T obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = getFieds(cls);
        try {
            getObjectFieldValueMap(map, fields, obj);
            Class<?> superClass = cls;
            while (true) {
                superClass = superClass.getSuperclass();
                fields = getFieds(superClass);
                getObjectFieldValueMap(map, fields, obj);
                if (superClass == BaseReqData.class) {
                    break;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }


    public static String getMD5Sign(Map<String, Object> map, Configure configure) {
        if (configure == null) {
            throw new RuntimeException("签名错误！");
        }
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + configure.getKey();
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }


    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String responseString, Configure configure) throws ParserConfigurationException, IOException, SAXException {

        Map<String, Object> map = XMLParser.getMapFromXML(responseString);
        Util.log(map.toString());

        String signFromAPIResponse = map.get("sign").toString();
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
            Util.log("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }
        Util.log("服务器回包里面的签名是:" + signFromAPIResponse);
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = Signature.getMD5Sign(map, configure);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            Util.log("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        Util.log("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param map
     * @param configure
     * @return
     */
    public static boolean checkIsSignValidFromResponseString(Map<String, Object> map, Configure configure) {


        String signFromAPIResponse = map.get("sign").toString();

        map.remove("sign");

        String signForAPIResponse = Signature.getMD5Sign(map, configure);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            Util.log("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
            return false;
        }
        Util.log("恭喜，API返回的数据签名验证通过!!!");
        return true;
    }

}
