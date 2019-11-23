package com.example.demo.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取 ObjectMapper对象用于json操作
     *
     * @return objectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * bean转json
     *
     * @return json
     */
    public static String beanToJson(Object o) throws JsonProcessingException {
        String json;
        json = getObjectMapper().writeValueAsString(o);
        return json;
    }

    /**
     * bean转json
     *
     * @return bean
     */
    public static <T> T jsonToBean(String json, Class<T> o) throws IOException {
        T result;
        result = getObjectMapper().readValue(json, o);
        return result;
    }

    /**
     * bean转换
     *
     * @return T
     */
    public static <T> T convertValue(Object o, TypeReference<?> toValueTypeRef) {
        return (T) getObjectMapper().convertValue(o, toValueTypeRef);
    }

    /**
     * bean转换
     *
     * @return T
     */
    public static <T> T convertValue(Object o, Class<T> toValueType) {
        return getObjectMapper().convertValue(o, toValueType);
    }

    /**
     * bean转换
     *
     * @return T
     */
    public static <T> T convertValue(Object o, JavaType toValueType) {
        return getObjectMapper().convertValue(o, toValueType);
    }

}
