package top.zcwfeng.sqlite;

public class UserContacts {

    public static final String TAG = "David";

    public static final String DATABASE_NAME = "user.db";
    public static final String DATABASE_NAME1 = "user.db1";
    public static final String DATABASE_NAME2 = "user.db2";

    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "user";

    public static class TABLE_USER{
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String WEIGHT = "weight";
    }
}
