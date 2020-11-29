package top.zcwfeng.sqlite;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

public class SqlOperTest {

    /**
     * 1.创建数据表user
     * 表名 user
     * *数据表user表结构字段
     * 主键：id
     * 名字：name
     * 年龄：age:
     * 体重：weight
     * @param sqLiteDatabase
     */
    public void create(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " +
                "IF NOT EXISTS " +
                "user(" +
                "id int PRIMARY KEY AUTOINCREMENT," +
                "name text," +
                "age int," +
                "weight real)";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 2.删除数据表user
     *
     * @param sqLiteDatabase
     */
    public void drop(SQLiteDatabase sqLiteDatabase) {
        String sql = "DROP TABLE " +
                "IF EXISTS " +
                "user";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 3.给user表中新增一条数据
     *
     * @param sqLiteDatabase
     */
    public void insert(SQLiteDatabase sqLiteDatabase) {
        String sql = "INSERT INTO" +
                " user VALUES('zero',25,120)";
        sqLiteDatabase.execSQL(sql);
    }


    public void insertGeneral100(SQLiteDatabase sqLiteDatabase){
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name","zero"+i);
            contentValues.put("age",i);
            contentValues.put("weight",i+100);
            sqLiteDatabase.insert(UserContacts.TABLE_NAME,null,contentValues);
        }
        Log.i("Zero", "insertGeneral100: " + (System.currentTimeMillis() - startTime));
    }

    public void insertOptimize100(SQLiteDatabase sqLiteDatabase){
        long startTime = System.currentTimeMillis();
        sqLiteDatabase.beginTransaction();//开启一个事务
        try{
            for (int i = 0; i < 100; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name","zero"+i);
                contentValues.put("age",i);
                contentValues.put("weight",i+100);
                sqLiteDatabase.insertOrThrow(UserContacts.TABLE_NAME,null,contentValues);
            }
            sqLiteDatabase.setTransactionSuccessful();//将数据事务设置为成功
        }catch (Exception e){e.printStackTrace();}
        finally {
            sqLiteDatabase.endTransaction();//结束数据库事务
        }
        Log.i("Zero", "insertOptimize100: " + (System.currentTimeMillis() - startTime));
    }


    /**
     * 4.修改user表中id为2的名字改成“lance1”
     *
     * @param sqLiteDatabase
     */
    public void update(SQLiteDatabase sqLiteDatabase) {
        String sql = "UPDATE" +
                " user SET" +
                " name='lance1' " +
                " WHERE id=2";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 5.删除user表中id为2的记录
     *
     * @param sqLiteDatabase
     */
    public void delete(SQLiteDatabase sqLiteDatabase) {
        String sql = "DELETE FROM user WHERE id=1";
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 6.查询数据
     *
     * @param sqLiteDatabase
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void query(SQLiteDatabase sqLiteDatabase) {
        String sql = "SELECT * FROM user";
        /***这里得到的是一个游标*/
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null, null);
        if (cursor == null) {
            return;
        }
        /***循环游标得到数据*/
        while (cursor.moveToNext()) {
            Log.d("Zero", "id=" + cursor.getInt(0) + "，name=" + cursor.getString(1) + "，age=" + cursor.getInt(2)
            + "，weight=" + cursor.getInt(3));
        }
        /***记得操作完将游标关闭*/
        cursor.close();
    }

    /**
     * 防止sql注入
     * @param sqLiteDatabase
     * @param query
     */
    public void query1(SQLiteDatabase sqLiteDatabase,String query) {
        String sql = "SELECT * FROM user WHERE name LIKE "+ query;
        String sql1 = "SELECT * FROM user WHERE name LIKE ?";
        String[] selectionArgs = new String[]{query};
        /***这里得到的是一个游标*/
        Cursor cursor = sqLiteDatabase.rawQuery(sql1, selectionArgs, null);
        if (cursor == null) {
            return;
        }
        /***循环游标得到数据*/
        while (cursor.moveToNext()) {
            Log.d("Zero", "id=" + cursor.getInt(0) + "，name=" + cursor.getString(1) + "，age=" + cursor.getInt(2)
                    + "，weight=" + cursor.getInt(3));
        }
        /***记得操作完将游标关闭*/
        cursor.close();
    }
}
