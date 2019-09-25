package top.zcwfeng.usedatas;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UserTypeAdapter extends TypeAdapter<UserType> {

    @Override
    public void write(JsonWriter jsonWriter, UserType user) throws IOException {
        //流式序列化成对象开始
        jsonWriter.beginObject();
        //将Json的Key值都指定为大写字母开头
        jsonWriter.name("Name").value(user.getName());
        jsonWriter.name("Age").value(user.getAge());
        jsonWriter.name("Sex").value(user.isSex());
        //流式序列化结束
        jsonWriter.endObject();
    }

    @Override
    public UserType read(JsonReader jsonReader) throws IOException {
        UserType user = new UserType();
        //流式反序列化开始
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            switch (jsonReader.nextName()) {
                //首字母大小写均合法
                case "name":
                case "Name":
                    user.setName(jsonReader.nextString());
                    break;
                case "age":
                    user.setAge(jsonReader.nextInt());
                    break;
                case "sex":
                    user.setSex(jsonReader.nextBoolean());
                    break;
            }

        }
        //流式反序列化结束
        jsonReader.endObject();
        return user;
    }

}