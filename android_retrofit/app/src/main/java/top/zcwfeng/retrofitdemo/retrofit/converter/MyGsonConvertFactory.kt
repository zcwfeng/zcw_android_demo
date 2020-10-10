package top.zcwfeng.retrofitdemo.retrofit.converter

import android.util.JsonToken
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.Writer
import java.lang.reflect.Type
import java.nio.charset.Charset

class MyGsonConvertFactory(private val gson:Gson): Converter.Factory() {

    companion object{
        fun create() = MyGsonConvertFactory(Gson())
        fun create(gson:Gson?):MyGsonConvertFactory{
            if(gson == null){
                throw NullPointerException("gson == null")
            }
            return MyGsonConvertFactory(gson)
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
       val adapter = gson.getAdapter(TypeToken.get(type))
       return null
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adaptation = gson.getAdapter(TypeToken.get(type))
        return null
    }

    private class MyGsonResponsesBodyConvert<T>(

        private val gson:Gson,
        private val  adapter:TypeAdapter<T>
    ):Converter<ResponseBody,T>{
        override fun convert(value: ResponseBody): T? {
           val jsonReader:JsonReader = gson.newJsonReader(value.charStream())

            return try {
                val result = adapter.read(jsonReader)
                if(jsonReader.peek() != JsonToken.END_DOCUMENT){
                    throw JsonIOException("JSON document was not fully consumed.")
                }
                result
            }finally {
                value.close()
            }
        }


    }


    private class MyGsonRequestBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) :
        Converter<T, RequestBody> {
        @Throws(IOException::class)
        override fun convert(value: T): RequestBody {
            val buffer = Buffer()
            val writer: Writer =
                OutputStreamWriter(buffer.outputStream(), UTF_8)
            val jsonWriter: JsonWriter = gson.newJsonWriter(writer)
            adapter.write(jsonWriter, value)
            jsonWriter.close()
            return RequestBody.create(
                MEDIA_TYPE,
                buffer.readByteString()
            )
        }

        companion object {
            private val MEDIA_TYPE: MediaType = MediaType.get("application/json; charset=UTF-8")
            private val UTF_8: Charset = Charset.forName("UTF-8")
        }

    }


}