package top.zcwfeng.libpermissionhelper;

public interface PermissionProxy<T> {

    public void grant(int requestCode,T source,String[] permissions);
    public void denied(int requestCode,T source,String[] permissions);
    public boolean rational(int requestCode,T source,String[] permissions,PermissionCallback callback);

    boolean needShowRationale(int requestCode, String... permission);

}
