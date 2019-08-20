package com.ryze.mvc.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xueLai on 2019/8/20.
 * 泛型操作的工具类,通过反射获得Class声明的范型Class.
 */
public class GenericsUtil {
    /**
     * 通过反射,获得方法输入参数第index个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String,Buyer> maps, List<String> names){}
     *
     * @param method method 方法
     * @param index  index 第几个输入参数
     * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
     */
    @SuppressWarnings("rawtypes")
    public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
        List<Class> result = new ArrayList<>();
        //返回此Method对象表示的方法的 形式参数类型 public void setUsername(String username){} 返回 [String];
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        if (index > genericParameterTypes.length || index < 0) {
            throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
        }
        Type genericParameterType = genericParameterTypes[index];
        if (genericParameterType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericParameterType;
            //getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组
            Type[] actualTypeArguments = aType.getActualTypeArguments();
            for (Type parameterArgType:actualTypeArguments) {
                Class parameterArgClass = (Class)parameterArgType;
                result.add(parameterArgClass);
            }
            return result;
        }
        return result;
    }
}
