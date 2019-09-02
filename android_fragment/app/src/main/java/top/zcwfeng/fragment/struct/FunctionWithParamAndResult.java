package top.zcwfeng.fragment.struct;

public abstract class FunctionWithParamAndResult<Result,Param> extends Function {
    public FunctionWithParamAndResult(String functionName) {
        super(functionName);
    }

    public abstract Result function(Param params);
}
