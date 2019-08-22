package top.zcwfeng.flowlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;
import java.util.List;

public class FlexBoxLayoutActivity extends AppCompatActivity {
    private List<String> mDatas = Arrays.asList("Android", "iOS", "Swift", "C#", "Java", "Python", "C++", "Unity3D", "Guava", "Nodejs", "React", "flutter", "Weex", "JavaScript", "Go", "Kotlin");
    FlexboxLayout flexboxLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fex_box_layout);
        generateButton();
    }

    public void addTag(View view) {
        TextView tag = (TextView) LayoutInflater.from(this).inflate(R.layout.item_textview,flexboxLayout,false);
        tag.setText(mDatas.get((int) (Math.random() * (mDatas.size() -1))));
        flexboxLayout.addView(tag);
    }

    private void generateButton() {
        flexboxLayout = findViewById(R.id.flexbox);


    }
}
