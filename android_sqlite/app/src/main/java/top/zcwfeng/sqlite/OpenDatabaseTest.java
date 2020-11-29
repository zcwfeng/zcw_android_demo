package top.zcwfeng.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.io.File;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OpenDatabaseTest {

    /**
     * 第一种方式：继承SQLiteOpenHelper打开或创建数据库
     * 特点：可以在升级数据库版本的时候在回调函数里面做相应的操作
     *
     * @param context
     */
    public void openSqlite1(Context context) {
        /**指定数据库的表名为info.db，版本号为1**/
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(context, UserContacts.DATABASE_NAME, null, UserContacts.DATABASE_VERSION);
        /**得到一个可写的数据库SQLiteDatabase对象**/
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        Log.d(UserContacts.TAG, sqLiteDatabase.getPath());
        /**查看改对象所创建的数据库**/
        displayDatabase(sqLiteDatabase);
    }

    /**
     * 第二种方式：Context.openOrCreateDatabase打开或创建数据库
     * 特点：可以指定数据库文件的操作模式
     *
     * @param context
     */
    public void openSqlite2(Context context) {
        /**指定数据库的名称为info2.db,并指定数据文件的操作模式为MODE_PRIVATE**/
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(UserContacts.DATABASE_NAME1, MODE_PRIVATE, null);
        /**查看改对象所创建的数据库**/
        displayDatabase(sqLiteDatabase);
    }

    /**
     * 第三种方式：SQLiteDatabase.openOrCreateDatabase打开或创建数据库
     * 特点：可以指定数据库文件的路径
     *
     * @param v
     */
    public void openSqlite3(View v) {
        File dataBaseFile = new File(Environment.getExternalStorageDirectory() + "/sqlite", UserContacts.DATABASE_NAME2);
        if (!dataBaseFile.getParentFile().exists()) {
            dataBaseFile.mkdirs();
        }
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dataBaseFile, null);
        displayDatabase(sqLiteDatabase);
    }


    /**
     * 查看由SQLiteDatabase创建的的数据库文件
     */
    public void displayDatabase(SQLiteDatabase sqLiteDatabase) {
        List<Pair<String, String>> ll = sqLiteDatabase.getAttachedDbs();
        for (int i = 0; i < ll.size(); i++) {
            Pair<String, String> p = ll.get(i);
            Log.d("Zero", p.first + "=" + p.second);

        }
    }
}
