package top.zcwfeng.dbdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "test.db";
    public static final int DB_VERSION = 2;


    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        Log.e(DB_NAME,"DBHelper");

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DBDao.SQL_CREATE_TABLE);
        Log.e(DB_NAME,"onCreate");

    }

    public void upToDbVersion3(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put("store", 100);
        database.update(DBDao.TABLE_NAME, values, null, null);
        Log.e(DB_NAME,"upToDbVersion3");

    }

    public static void upToDbVersion2(SQLiteDatabase database) {
        String updateSql = "alter table " + DBDao.TABLE_NAME + " add column store varchar(5)";
        database.execSQL(updateSql);
        Log.e(DB_NAME,"upToDbVersion2");

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version, int newVersion) {
        Log.e(DB_NAME,"onUpgrade");

        for (int i = version; i < newVersion; i++) {
            switch (i) {
                case 2:
                    upToDbVersion2(database);
                    break;
                case 3:
                    upToDbVersion3(database);
                    break;
                default:
                    break;
            }
        }
    }
}
