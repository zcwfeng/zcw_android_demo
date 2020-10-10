package top.zcwfeng.retrofitdemo.retrofit.calladapter

import androidx.lifecycle.LiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory: CallAdapter.Factory(){

    companion object{
        @JvmStatic
        fun create() = LiveDataCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val responseType: Type
        if(getRawType(returnType) != LiveData::class.java){
            throw IllegalStateException("return type must be parameterized")
        }
        val observableType = getParameterUpperBound(0,returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        responseType = if(rawObservableType == Response::class.java){
            if(observableType !is ParameterizedType){
                throw java.lang.IllegalStateException("Response must be parameterized")
            }
            getParameterUpperBound(0,observableType)
        }else{ observableType}
        return LiveDataCallAdapter<Any>(responseType)
    }

}

private class LiveDataCallAdapter<R>(private val responseType: Type): CallAdapter<R, LiveData<R>> {
    override fun adapt(call: Call<R>): LiveData<R> {
        return object : LiveData<R>(){
            private val startedFlag = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if(startedFlag.compareAndSet(false,true)){
                    call.enqueue(object: Callback<R> {
                        override fun onFailure(call: Call<R>, t: Throwable) {
                            postValue(null)
                        }

                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(response.body())
                        }

                    })
                }
            }
        }
    }

    override fun responseType(): Type = responseType

}