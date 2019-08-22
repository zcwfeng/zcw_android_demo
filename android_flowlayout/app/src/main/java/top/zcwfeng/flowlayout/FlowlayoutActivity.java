package top.zcwfeng.flowlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import top.zcwfeng.flowlayout.view.Flowlayout;

public class FlowlayoutActivity extends AppCompatActivity {

    private Flowlayout flowlayout;
    private List<String> mDatas = Arrays.asList("Android", "iOS", "Swift", "C#", "Java", "Python", "C++", "Unity3D", "Guava", "Nodejs", "React", "flutter", "Weex", "JavaScript", "Go", "Kotlin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        generateButton();


    }

    public void addTag(View view) {
        TextView tag = (TextView) LayoutInflater.from(this).inflate(R.layout.item_textview,flowlayout,false);
        tag.setText(mDatas.get((int) (Math.random() * (mDatas.size() -1))));
        flowlayout.addView(tag);
    }

    private void generateButton() {
        flowlayout = findViewById(R.id.zcw_flowlayout);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = new Button(this);
        button.setText("ADD");
        button.setLayoutParams(lp);

    }
}
