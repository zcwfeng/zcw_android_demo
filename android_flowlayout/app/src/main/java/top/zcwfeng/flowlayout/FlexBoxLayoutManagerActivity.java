package top.zcwfeng.flowlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import top.zcwfeng.flowlayout.adapter.FlextBoxLayoutManagerAdapter;

public class FlexBoxLayoutManagerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private FlextBoxLayoutManagerAdapter adapter;

    private List<String> randomDatas = Arrays.asList("Android", "iOS", "Swift", "C#", "Java", "Python", "C++", "Unity3D", "Guava", "Nodejs", "React", "flutter", "Weex", "JavaScript", "Go", "Kotlin");
    private List<String> mDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_box_layout_manager);

        for (int i = 0; i < 400; i++) {
            mDatas.add(tag());
        }
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new FlexboxLayoutManager(this));
        adapter = new FlextBoxLayoutManagerAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);
    }

    private String tag() {
        return randomDatas.get((int) (Math.random()* (randomDatas.size() - 1)));
    }
}