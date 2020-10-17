package top.zcwfeng.base.customview.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final
class GenericUtils {
    public GenericUtils() {
    }

    public static Class<?> getGenericType(Object obj){
        Class<?> genericTye = null;
        Type generType = obj.getClass().getGenericSuperclass();
        if(generType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) generType;
            Type types[] = parameterizedType.getActualTypeArguments();

            if(types != null && types.length > 0) {
                Type type = types[0];
                if(type instanceof Class){
                    genericTye = (Class<?>) type;
                }
            }
        }
        return genericTye;
    }
}
