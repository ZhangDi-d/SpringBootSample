package com.ryze.mvc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.paul.convertor.PriTypeConverter;
import com.paul.convertor.PrimitiveType;
import com.paul.mvc.annotation.RequestParam;
import com.paul.mvc.util.GenericsUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.lang3.CharEncoding;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xueLai on 2019/8/20.
 * HTTP请求参数解析器, 支持GET, POST
 */
public class RequestParseUtil {

    /**
     * 获取请求参数 Map
     */
    public static Map<String, List<String>> getParamMap(FullHttpRequest request) {
        Map<String, List<String>> paramMap = new HashMap<>();
        HttpMethod method = request.method();
        if (method.equals(HttpMethod.GET)) {
            String uri = request.uri();
            QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charset.forName("utf-8"));
            paramMap = queryDecoder.parameters();

        } else if (method.equals(HttpMethod.POST)) {
            paramMap = getPostParamMap(request);
        }

        return paramMap;

    }

    //目前支持最常用的 application/json 、application/x-www-form-urlencoded 几种 POST Content-type，可自行扩展！！！
    @SuppressWarnings("unchecked")
    public static Map<String, List<String>> getPostParamMap(FullHttpRequest fullRequest) {
        Map<String, List<String>> paramMap = new HashMap<>();
        HttpHeaders headers = fullRequest.headers();
        String contentType = getContentType(headers);
        if ("application/json".equals(contentType)) {
            String s = fullRequest.content().toString(Charset.forName("utf-8"));
            JSONObject jsonObject = JSON.parseObject(s);
            for (Map.Entry<String, Object> item : jsonObject.entrySet()) {
                String key = item.getKey();
                Object value = item.getValue();
                Class<?> valueType = value.getClass();

                List<String> valueList = null;
                if (paramMap.containsKey(key)) {
                    valueList = paramMap.get(key);
                } else {
                    valueList = new ArrayList<String>();
                }

                if (PrimitiveType.isPriType(valueType)) {
                    valueList.add(value.toString());
                    paramMap.put(key, valueList);

                } else if (valueType.isArray()) {
                    int length = Array.getLength(value);
                    for (int i = 0; i < length; i++) {
                        String arrayItem = String.valueOf(Array.get(value, i));
                        valueList.add(arrayItem);
                    }
                    paramMap.put(key, valueList);

                } else if (List.class.isAssignableFrom(valueType)) {
                    if (valueType.equals(JSONArray.class)) {
                        JSONArray jArray = JSONArray.parseArray(value.toString());
                        for (int i = 0; i < jArray.size(); i++) {
                            valueList.add(jArray.getString(i));
                        }
                    } else {
                        valueList = (ArrayList<String>) value;
                    }
                    paramMap.put(key, valueList);

                } else if (Map.class.isAssignableFrom(valueType)) {
                    Map<String, String> tempMap = (Map<String, String>) value;
                    for (String tempKey : tempMap.keySet()) {
                        List<String> tempList = new ArrayList<>();
                        tempList.add(tempMap.get(tempKey));
                        paramMap.put(tempKey, tempList);
                    }
                }
            }

        } else if ("application/x-www-form-urlencoded".equals(contentType)) {
            String jsonStr = fullRequest.content().toString(Charset.forName("utf-8"));
            QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
            paramMap = queryDecoder.parameters();
        }
        return paramMap;

    }

    /**
     * GET 参数解析
     *
     * @param paramMap
     * @param type
     * @param
     * @param method
     * @param index
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object getParamValue(Map<String, List<String>> paramMap, Class<?> type, RequestParam requestParam, Method method, int index) throws InstantiationException, IllegalAccessException {
        Object value = null;
        if (Map.class.isAssignableFrom(type)) {
            if (index > 0) {
                throw new RuntimeException("Must have only one Map type parameter");
            }

            List<Class> types = com.paul.mvc.util.GenericsUtil.getMethodGenericParameterTypes(method, index);
            if (types.size() == 2 && (types.get(0) != String.class || types.get(1) != String.class)) {
                throw new RuntimeException("Map type parameter must both be String, Occuring Point: " + method.toGenericString());
            }

            Map<String, String> valueMap = new HashMap<String, String>();
            for (String paramKey : paramMap.keySet()) {
                List<String> valueList = paramMap.get(paramKey);
                valueMap.put(paramKey, valueList.get(0));
            }
            value = valueMap;
        } else {
            List<String> params = paramMap.get(requestParam.value());
            if (params != null) {
                if (PrimitiveType.isPriType(type)) {
                    value = PriTypeConverter.getInstance().convertValue(params.get(0), type);

                } else if (type.isArray()) {
                    Object[] objArray = params.toArray();
                    String[] strArray = objArray2StrArray(objArray);
                    value = PriTypeConverter.getInstance().convertValue(strArray, type);

                } else if (List.class.isAssignableFrom(type)) {
                    List<Object> list = null;
                    List<Class> types = GenericsUtil.getMethodGenericParameterTypes(method, index);
                    Class<?> listType = types.size() == 1 ? types.get(0) : String.class;
                    if (List.class == type) {
                        list = new ArrayList<Object>();
                    } else {
                        list = (List<Object>) type.newInstance();
                    }
                    for (int i = 0; i < params.size(); i++) {
                        if (params.get(i).length() > 0) {
                            list.add(PriTypeConverter.getInstance().convertValue(params.get(i), listType));
                        }
                    }
                    value = list;
                }
            }
        }

        return value;
    }

    private static String[] objArray2StrArray(Object[] objArray) {
        int length = objArray.length;
        String[] strArray = new String[length];
        for (int i = 0; i < length; i++) {
            strArray[i] = String.valueOf(objArray[i]);
        }
        return strArray;
    }

    private static String getContentType(HttpHeaders headers) {
        String contentType = headers.get("Content-Type");
        String[] list = contentType.split(";");
        return list[0];
    }
}
