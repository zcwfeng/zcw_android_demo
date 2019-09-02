package top.zcwfeng.fragment.struct;

import android.text.TextUtils;

import java.util.HashMap;

public class FunctionManager {

    private FunctionManager(){
        mFunctionNoParamsNotResult = new HashMap<>();
        mFunctionWithParamOnly = new HashMap<>();
        mFunctionWithResultOnly = new HashMap<>();
        mFunctionWithParamAndResult = new HashMap<>();
    }

    private static FunctionManager _instance;

    public static FunctionManager getInstance(){
        if(_instance == null) {
            _instance = new FunctionManager();
        }
        return _instance;
    }

    private HashMap<String,FunctionNoParamsNotResult> mFunctionNoParamsNotResult;
    private HashMap<String,FunctionWithParamOnly> mFunctionWithParamOnly;
    private HashMap<String,FunctionWithResultOnly> mFunctionWithResultOnly;
    private HashMap<String, FunctionWithParamAndResult> mFunctionWithParamAndResult;


    public FunctionManager addFunction(FunctionNoParamsNotResult function){
        mFunctionNoParamsNotResult.put(function.mFunctionName,function);
        return this;
    }

    public FunctionManager addFunction(FunctionWithResultOnly function){
        mFunctionWithResultOnly.put(function.mFunctionName,function);
        return this;
    }

    public FunctionManager addFunction(FunctionWithParamOnly function){
        mFunctionWithParamOnly.put(function.mFunctionName,function);
        return this;
    }

    public FunctionManager addFunction(FunctionWithParamAndResult function){
        mFunctionWithParamAndResult.put(function.mFunctionName,function);
        return this;
    }

    public void invokeFunc(String funcName) throws FunctionException {
        if(TextUtils.isEmpty(funcName)){
            return;
        }

        if(mFunctionNoParamsNotResult != null) {
           FunctionNoParamsNotResult f =  mFunctionNoParamsNotResult.get(funcName);
            if(f != null) {
                f.function();
            } else {
                throw new FunctionException("has not found:" + funcName);
            }
        }
    }


    public <Result>Result invokeFunc(String funcName,Class<Result> c) throws FunctionException {
        if(TextUtils.isEmpty(funcName)){
            return null;
        }

        if(mFunctionWithResultOnly != null) {
            FunctionWithResultOnly f =  mFunctionWithResultOnly.get(funcName);
            if(f != null) {
                if(c!= null) {
                    return c.cast(f.function());
                } else {
                    return (Result) f.function();
                }
            } else {
                throw new FunctionException("has not found:" + funcName);
            }
        }

        return null;
    }


    public <Result,Param>Result invokeFunc(String funcName,Class<Result> c,Param param) throws FunctionException {
        if(TextUtils.isEmpty(funcName)){
            return null;
        }

        if(mFunctionWithParamAndResult != null) {
            FunctionWithParamAndResult f =  mFunctionWithParamAndResult.get(funcName);
            if(f != null) {
                if(c!= null) {
                    return c.cast(f.function(param));
                } else {
                    return (Result) f.function(param);
                }
            } else {
                throw new FunctionException("has not found:" + funcName);
            }
        }

        return null;
    }


    public <Param> void invokeFunc(String funcName, Param param) throws FunctionException {
        if(TextUtils.isEmpty(funcName)){
            return;
        }

        if(mFunctionWithParamOnly != null) {
            FunctionWithParamOnly f =  mFunctionWithParamOnly.get(funcName);
            if(f != null) {
                    f.function(param);
            }
            else {
                throw new FunctionException("has not found:" + funcName);
            }
        }

    }
}
