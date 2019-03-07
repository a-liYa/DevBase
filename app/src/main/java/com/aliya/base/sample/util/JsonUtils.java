package com.aliya.base.sample.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Json工具类封装
 * <p>门面模式，随意切换fastJson</p>
 *
 * @author a_liYa
 * @date 16/6/27 下午3:12.
 */
public class JsonUtils {

    static private Gson gson = new Gson();

    /**
     * 把json字符串解析成对象
     *
     * @param jsonString json字符串
     * @param cls        目标对象class
     * @return
     */
    public static <T> T parseObject(String jsonString, Class<T> cls) {
        return gson.fromJson(jsonString, cls);
    }

    /**
     * 把json字符串解析成对象
     *
     * @param jsonString json字符串
     * @param typeOfT    目标对象 type
     * @return
     */
    public static <T> T parseObject(String jsonString, Type typeOfT) {
        return gson.fromJson(jsonString, typeOfT);
    }

    /**
     * 把json字符串解析成包含泛型对象
     *
     * @param jsonString json字符串
     * @param cls        目标对象class
     * @param generics   泛型类型class参数集合
     * @return
     */
    public static <T> T parseObject(String jsonString, Class<T> cls, Class<?>... generics) {
        return gson.fromJson(jsonString, type(cls, generics));
    }

    /**
     * json字符串解析成List数据
     *
     * @param jsonString json字符串
     * @param generic    List数据泛型的clazz
     * @return
     */
    public static <T> List<T> parseArray(String jsonString, Class<T> generic) {
        return gson.fromJson(jsonString, type(List.class, generic));
    }


    /**
     * 把对象转换成json字符串
     *
     * @param object 对象
     * @return
     */
    public static String toJsonString(Object object) {
        return gson.toJson(object);
    }

    /**
     * 支持泛型的type
     *
     * @param raw  数据类型的clazz
     * @param args 数据类型泛型的clazz
     * @return
     */
    private static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {

            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}