package top.zcwfeng.android_apt.retrofit;

public abstract class ParameterHandler {
    abstract void apply(ServiceMethod serviceMethod,String value);
    static class QueryParameterHandler extends ParameterHandler {
        String key;

        public QueryParameterHandler(String key) {
            this.key = key;
        }

        @Override
        void apply(ServiceMethod serviceMethod, String value) {
            serviceMethod.addQueryParameter(key,value);
        }
    }


    static class FieldParameterHandler extends ParameterHandler {
        public FieldParameterHandler(String key) {
            this.key = key;
        }

        String key;
        @Override
        void apply(ServiceMethod serviceMethod, String value) {
            serviceMethod.addFieldParameter(key,value);

        }
    }
}
