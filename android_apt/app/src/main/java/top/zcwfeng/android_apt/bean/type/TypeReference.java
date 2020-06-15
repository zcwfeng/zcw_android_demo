package top.zcwfeng.android_apt.bean.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
// protected 不同的包对应加上{}
public class TypeReference<T> {
    Type mType;

    protected TypeReference() {
        Type genericSuperClass = getClass().getGenericSuperclass();
        ParameterizedType type = (ParameterizedType) genericSuperClass;
        Type[] types = type.getActualTypeArguments();
        mType = types[0];
    }

    public Type getmType() {
        return mType;
    }
}