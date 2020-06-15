package top.zcwfeng.android_apt.bean;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import top.zcwfeng.android_apt.bean.type.TypeReference;

public class Response<T> {

    T data;

    public Response(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    int code;
    String message;

    static class Data {
        String result;

        public Data(String result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "result='" + result + '\'' +
                    '}';
        }
    }

//    static class TypeReference<T>{
//        Type mType;
//        protected TypeReference() {
//            Type genericSuperClass = getClass().getGenericSuperclass();
//            ParameterizedType type = (ParameterizedType) genericSuperClass;
//            Type[] types = type.getActualTypeArguments();
//            mType = types[0];
//        }
//
//        public Type getmType() {
//            return mType;
//        }
//    }

    public static void main(String[] args) {
        Response<Data> dataResponse =
                new Response(new Data("数据"), 1, "success");
        Gson gson = new Gson();
        String json  = gson.toJson(dataResponse);
        System.out.println(json);
        // 反序列化......
//        Type type = new TypeToken<Response<Data>>(){}.getType();

        Type typeReference = new TypeReference<Response<Data>>(){}.getmType();
        Response<Data> response =  gson.fromJson(json,typeReference);
        System.out.println(response.data.getClass());
    }
}
