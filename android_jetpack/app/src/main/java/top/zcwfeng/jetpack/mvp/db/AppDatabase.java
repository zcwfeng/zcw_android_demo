package top.zcwfeng.jetpack.mvp.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {top.zcwfeng.jetpack.mvp.db.Student.class},version = 3,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract top.zcwfeng.jetpack.mvp.db.StudentDao studentDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class
                    , "davidDBMVP")
                    .allowMainThreadQueries()

//                    .fallbackToDestructiveMigrationOnDowngrade()
                    // 强制升级非常暴力不要用数据会没
//                    .fallbackToDestructiveMigration()
//                    .fallbackToDestructiveMigrationFrom()
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table student add column flag2 integer not null default 1");
        }
    };

    protected AppDatabase() {
    }



}
