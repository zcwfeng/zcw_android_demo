package top.zcwfeng.fragment.struct;

public abstract class FunctionWithParamOnly<Param> extends Function {
    public FunctionWithParamOnly(String functionName) {
        super(functionName);
    }

//    public abstract void function(Param ... params);

    public abstract void function(Param params);

}
