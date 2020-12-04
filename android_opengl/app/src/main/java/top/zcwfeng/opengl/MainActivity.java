package top.zcwfeng.opengl;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import top.zcwfeng.opengl.widget.CameraView;
import top.zcwfeng.opengl.widget.RecordButton;

public class MainActivity extends AppCompatActivity implements
        RecordButton.OnRecordListener, RadioGroup.OnCheckedChangeListener {
    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = findViewById(R.id.cameraView);

        RecordButton btn_record = findViewById(R.id.btn_record);
        btn_record.setOnRecordListener(this);

        //速度
        RadioGroup rgSpeed = findViewById(R.id.rg_speed);
        rgSpeed.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.btn_extra_slow:
                cameraView.setSpeed(CameraView.Speed.MODE_EXTRA_SLOW);
                break;
            case R.id.btn_slow:
                cameraView.setSpeed(CameraView.Speed.MODE_SLOW);
                break;
            case R.id.btn_normal:
                cameraView.setSpeed(CameraView.Speed.MODE_NORMAL);
                break;
            case R.id.btn_fast:
                cameraView.setSpeed(CameraView.Speed.MODE_FAST);
                break;
            case R.id.btn_extra_fast:
                cameraView.setSpeed(CameraView.Speed.MODE_EXTRA_FAST);
                break;
        }
    }

    @Override
    public void onRecordStart() {
        cameraView.startRecord();
    }

    @Override
    public void onRecordStop() {
        cameraView.stopRecord();
    }
}
