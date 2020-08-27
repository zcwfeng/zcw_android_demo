package top.zcwfeng.paging_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student2.class}, version = 1, exportSchema = false)
public abstract class StudentsDatabase extends RoomDatabase {
    private static StudentsDatabase instance;

    static synchronized StudentsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, StudentsDatabase.class, "students_paging_database")
                    .build();
        }
        return instance;
    }

    abstract StudentDao getStudentDao();
}
