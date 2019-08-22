package top.zcwfeng.flowlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import top.zcwfeng.flowlayout.adapter.TagFlowLayoutAdapter;
import top.zcwfeng.flowlayout.view.TagFlowLayout;

public class TagFlowLayoutActivity extends AppCompatActivity {
    private List<String> mDatas = Arrays.asList("Android", "iOS", "Swift", "C#", "Java", "Python", "C++", "Unity3D", "Guava", "Nodejs", "React", "flutter", "Weex", "JavaScript", "Go", "Kotlin");
    private TagFlowLayout tagFlowLayout;
    private TagFlowLayoutAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_flow_layout);
        tagFlowLayout = findViewById(R.id.tagflowlayout);
        tagFlowLayout.setMaxSelectedCount(3);
        tagFlowLayout.setmAdapter(
                mAdapter = new TagFlowLayoutAdapter() {
                    @Override
                    public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
                        return inflater.inflate(R.layout.item_select_tag, parent, false);
                    }

                    @Override
                    public int getItemCount() {
                        return mDatas.size();
                    }

                    @Override
                    public void bindView(View viewholder, int positon) {
                        TextView tv = viewholder.findViewById(R.id.tv);
                        tv.setText(mDatas.get((int) (Math.random() * mDatas.size() - 1)));
                    }



                    @Override
                    public void tipForSelectMax(View view, int mMaxSelectedCount) {
                        super.tipForSelectMax(view, mMaxSelectedCount);
                        Toast.makeText(TagFlowLayoutActivity.this, "最多选择" + mMaxSelectedCount + "个", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemSelecte(View view, int position) {
                        super.onItemSelecte(view, position);
                        TextView tv = view.findViewById(R.id.tv);
                        tv.setTextColor(Color.RED);
                    }

                    @Override
                    public void onItemUnSelecte(View view, int position) {
                        super.onItemUnSelecte(view, position);
                        TextView tv = view.findViewById(R.id.tv);
                        tv.setTextColor(Color.GRAY);
                    }

                    @Override
                    public void onItemViewClick(View view, int position) {
                        super.onItemViewClick(view,position);
                        Toast.makeText(TagFlowLayoutActivity.this, "选择" + tagFlowLayout.getSelectedItemPositionList().toString(), Toast.LENGTH_SHORT).show();


                    }
                });

    }


    public void clickChangeData(View view) {
        mDatas = Arrays.asList("Android", "iOS", "Swift", "C#", "Java", "Python", "C++");
        tagFlowLayout.setMaxSelectedCount(1);
        mAdapter.onDataNotifyChanged();

    }
}
