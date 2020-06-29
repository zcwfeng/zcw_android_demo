package top.zcwfeng.jetpack.room;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import top.zcwfeng.jetpack.R;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        DBTest t = new DBTest();
        t.start();
    }

    private class DBTest extends Thread {
        @Override
        public void run() {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class,
                    "davidDB").build();
            StudentDao dao = db.studentDao();
//            dao.insert(new Student("david1", "1234", 1));
//            dao.insert(new Student("david2", "1234", 2));
//            dao.insert(new Student("david3", "1234", 3));
//            dao.insert(new Student("david4", "1234", 4));
//            List<Student> list = dao.getAll();
//            Log.d("zcw:::", "db: " + list.toString());
//
//            Student db2 = dao.findByName("david3");
//            Log.d("zcw:::", "run: "+db2.toString());
//            List<Student> allId = dao.getAllId(new int[]{1,2,3,4});
//            Log.d("zcw:::", "run: "+ allId.toString());
            List<StudentTuple> studentTuples = dao.getRecord();
            Log.d("zcw:::", "run: " + studentTuples.toString());
        }
    }
}