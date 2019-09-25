package top.zcwfeng.usedatas;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTypeAdapterFactory(){
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new TypeAdapterFactory() {
            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                //如果 Gson 需要与 User 类相关的 TypeAdapter ，则返回我们自己定义的 TypeAdapter 对象
                if (typeToken.getType().getTypeName().equals(User.class.getTypeName())) {
                    return (TypeAdapter<T>) new UserTypeAdapter();
                }
                //找不到则返回null
                return null;
            }
        }).create();
    }

    @Test
    public void testJsonDeserializer(){
        Gson gson = new GsonBuilder().registerTypeAdapter(UserType.class, new JsonDeserializer<UserType>() {
            @Override
            public UserType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String name = null;
                //同时支持 userName 和 name 两种情况
                if (jsonObject.has("userName")) {
                    name = jsonObject.get("userName").getAsString();
                } else if (jsonObject.has("name")) {
                    name = jsonObject.get("name").getAsString();
                }
                int age = jsonObject.get("age").getAsInt();
                boolean sex = jsonObject.get("sex").getAsBoolean();
                return new UserType(name, age, sex);
            }
        }).create();
        String json = "{\"userName\":\"leavesC\",\"sex\":true,\"age\":24}";
        User user = gson.fromJson(json, User.class);
        System.out.println();
        System.out.println(user);

        json = "{\"name\":\"leavesC\",\"sex\":true,\"age\":24}";
        user = gson.fromJson(json, User.class);
        System.out.println();
        System.out.println(user);
    }

    @Test
    public void testJsonSerializer(){
        Gson gson = new GsonBuilder().registerTypeAdapter(UserType.class, new JsonSerializer<UserType>() {
            @Override
            public JsonElement serialize(UserType user, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("NameHi", user.getName());
                jsonObject.addProperty("Sex", user.isSex());
                jsonObject.addProperty("Age", user.getAge());
                return jsonObject;
            }
        }).create();
        User user = new User("leavesC", 24, true);
        System.out.println();
        System.out.println(gson.toJson(user));
    }

    @Test
    public void testUserTypeAdapter(){
        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserTypeAdapter()).create();
        UserType user = new UserType("leavesC", 24, true);
        System.out.println();
        System.out.println(gson.toJson(user));

        String json = "{\"Name\":\"leavesC\",\"age\":24,\"sex\":true}";
        user = gson.fromJson(json, UserType.class);
        System.out.println();
        System.out.println(user);
    }

    @Test
    public void testGsonDate(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()//格式化输出
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//格式化时间
                .create();
        Date date = new Date();
        StrategiesDate strategies = new StrategiesDate(date, new Date(date.getTime() + 1000000));
        System.out.println();
        System.out.println(gson.toJson(strategies));

        String json = "{\n" +
                "  \"date\": \"2018-03-17 19:38:50:033\",\n" +
                "  \"date2\": \"2018-03-17 19:55:30:033\"\n" +
                "}";
        System.out.println();
        System.out.println(gson.fromJson(json, StrategiesDate.class));
    }

    @Test
    public void testGsonFormat(){
        Gson gson = new GsonBuilder()
                .serializeNulls() //输出null
                .setPrettyPrinting()//格式化输出
                .create();
        Strategies strategies = new Strategies(null, 24, 22.333);
        System.out.println();
        System.out.println(gson.toJson(strategies));
    }

    @Test
    public void testGsonStregies(){
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                //排除指定字段名
                return fieldAttributes.getName().equals("intField");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                //排除指定字段类型
                return aClass.getName().equals(double.class.getName());
            }
        }).create();

        Strategies strategies = new Strategies("stringField", 111, 11.22);
        System.out.println();
        System.out.println(gson.toJson(strategies));

        String json = "{\"stringField\":\"stringField\",\"intField\":111,\"doubleField\":11.22}";
        strategies = gson.fromJson(json, Strategies.class);
        System.out.println();
        System.out.println(strategies);

        System.out.println(">>>>>>>>>>>>>>");

        Gson gson2 = new GsonBuilder()
                .serializeNulls() //输出null
                .create();
        Strategies strategies2 = new Strategies(null, 24, 22.333);
        System.out.println();
        System.out.println(gson2.toJson(strategies2));
    }

    @Test
    public void testGsonModifier(){
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE, Modifier.STATIC).create();
        ModifierSample modifierSample = new ModifierSample();
        System.out.println(gson.toJson(modifierSample));
    }

    @Test
    public void testGsonSinceUntil(){
        Gson gson = new GsonBuilder().setVersion(1.6).create();
        UserSinceUntil user = new UserSinceUntil("A", "B", "C", "D", "E");
        System.out.println();
        System.out.println(gson.toJson(user));

        String json = "{\"a\":\"A\",\"b\":\"B\",\"c\":\"C\",\"d\":\"D\",\"e\":\"E\"}";
        user = gson.fromJson(json, UserSinceUntil.class);
        System.out.println();
        System.out.println(user.toString());
    }

    @Test
    public void testGsonExpose(){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        UserExpose user = new UserExpose("A", "B", "C", "D", "E");
        System.out.println();
        System.out.println(gson.toJson(user));

        String json = "{\"a\":\"A\",\"b\":\"B\",\"c\":\"C\",\"d\":\"D\",\"e\":\"E\"}";
        user = gson.fromJson(json, UserExpose.class);
        System.out.println(user.toString());
    }

    @Test
    public void toJsonUse(){
        //序列化
        User user = new User("leavesC", 24, true);
        Gson gson = new Gson();
        System.out.println(gson.toJson(user));
    }

    @Test
    public void toGsonUse(){
        //反序列化
                String userJson = "{\"userName\":\"leavesC\",\"age\":24,\"sex\":true}";

//        String userJson = "{\"name\":\"leavesC\",\"age\":24,\"sex\":true}";
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        System.out.println(user);
    }


    @Test//@SerializedName
    public void serialNameUse(){
        //反序列化
        Gson gson = new Gson();
        String userJson = "{\"userName\":\"leavesC\",\"age\":24,\"sex\":true}";
        User user = gson.fromJson(userJson, User.class);
        System.out.println();
        System.out.println(user);

        userJson = "{\"user_name\":\"leavesC\",\"age\":24,\"sex\":true}";
        user = gson.fromJson(userJson, User.class);
        System.out.println();
        System.out.println(user);

        userJson = "{\"Name\":\"leavesC\",\"age\":24,\"sex\":true}";
        user = gson.fromJson(userJson, User.class);
        System.out.println();
        System.out.println(user);
    }


    @Test
    public void generateJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("String", "leavesC");
        jsonObject.addProperty("Number_Integer", 23);
        jsonObject.addProperty("Number_Double", 22.9);
        jsonObject.addProperty("Boolean", true);
        jsonObject.addProperty("Char", 'c');
        System.out.println(jsonObject);
    }

    @Test
    public void jsonElementUse(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("String", "leavesC");
        jsonObject.addProperty("Number", 23);
        jsonObject.addProperty("Number", 22.9);
        jsonObject.addProperty("Boolean", true);
        jsonObject.addProperty("Char", 'c');

        JsonObject jsonElement = new JsonObject();
        jsonElement.addProperty("Boolean", false);
        jsonElement.addProperty("Double", 25.9);
        jsonElement.addProperty("Char", 'c');
        jsonObject.add("JsonElement", jsonElement);

        System.out.println(jsonObject);
    }

    @Test
    public void jsonArrConvertUse(){
        //Json数组 转为 字符串数组
        Gson gson = new Gson();
        String jsonArray = "[\"https://github.com/leavesC\",\"https://www.jianshu.com/u/9df45b87cfdf\",\"Java\",\"Kotlin\",\"Git\",\"GitHub\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);
        System.out.println("Json数组 转为 字符串数组: ");
        for (String string : strings) {
            System.out.println(string);
        }
        //字符串数组 转为 Json数组
        jsonArray = gson.toJson(strings, String[].class);
        System.out.println("\n字符串数组 转为 Json数组: ");
        System.out.println(jsonArray);
    }

    @Test
    public void jsonListConvertUse(){
        //Json数组 转为 List
        Gson gson = new Gson();
        String jsonArray = "[\"https://github.com/leavesC\",\"https://www.jianshu.com/u/9df45b87cfdf\",\"Java\",\"Kotlin\",\"Git\",\"GitHub\"]";
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {
        }.getType());
        System.out.println("\nJson数组 转为 List: ");
        for (String string : stringList) {
            System.out.println(string);
        }
        //List 转为 Json数组
        jsonArray = gson.toJson(stringList, new TypeToken<List<String>>() {
        }.getType());
        System.out.println("\nList 转为 Json数组: ");
        System.out.println(jsonArray);
    }

    public void HelloWord() {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().create();
    }
}