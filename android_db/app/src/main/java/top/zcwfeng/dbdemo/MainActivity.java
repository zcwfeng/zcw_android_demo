package top.zcwfeng.dbdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.dbdemo.db.DBDao;
import top.zcwfeng.dbdemo.db.Student;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBDao dao;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        findViewById(R.id.click_insert).setOnClickListener(this);

        dao = DBDao.getInstance();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.click_insert:
                Student student = new Student("小明", 20, "三年二班", "man","");
                DBDao.getInstance().insert(student);
                break;
        }
    }
}
