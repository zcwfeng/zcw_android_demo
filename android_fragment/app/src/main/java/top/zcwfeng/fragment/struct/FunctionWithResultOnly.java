package top.zcwfeng.fragment.struct;

public abstract class FunctionWithResultOnly<Result> extends Function {
    public FunctionWithResultOnly(String functionName) {
        super(functionName);
    }

    public abstract Result function();
}
