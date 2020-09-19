package com.zero.matrixdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        ListView mListView = (ListView) findViewById(R.id.listView);
        initListView(mListView);
        

         //setContentView(new CanvasDrawView(this));
        // setContentView(new CanvasTransformationView(this));
         //setContentView(new CanvasStackView(this));
        // setContentView(new CanvasComposeView(this));
        // setContentView(new TextDrawView(this));
         //setContentView(new BitmapDrawView(this));
         //setContentView(new MatrixsView(this));
        // setContentView(new CommonImgEffectView(this));
        // setContentView(new CanvasView(this));

    }

    private void initListView(ListView listView) {
        final String[] mListTitle = { 
                "01 canvas基本图形的绘制",
                "02 canvas变换",
                "03 Canvas保存与回滚", 
                "04 Canvas合成",
                "05 文字绘制方法",
                "06 绘制圆形头像",
                "07 Matrix矩阵", 
                "08 自定义drawable",
                "09 浏览图片demo",};

        final String[] mListStr = {
                "通过canvas、使用Paint进行基本图形的绘制",
                "通过canvas实现平移、旋转、缩放、仿射变换",
                "canvas的堆栈操作实现Canvas保存与回滚",
                "介绍Canvas如何合成", 
                "常用的文字绘制方法",
                "介绍两种绘制圆形头像的方法，第二种在图像的边缘有锯齿",
                "Matrix矩阵的各种操作，内有多个例子，可以自己调试",
                "自定义drawable实现一键清除动画", 
                "通过拖动控制顶点，实现图片的旋转、平移、缩放，还有view的事件响应",
                
        };

        final Class<?>[] mActivityList = new Class<?>[] {
//                CanvasDrawActivity.class,
                Xfermodes.class,
                CanvasTransformationActivity.class,
                CanvasStackActivity.class,
                CanvasComposeActivity.class,
                TextDrawActivity.class,
                BitmapDrawActivity.class,
                MatrixsActivity.class,
                SelfDrawableActivity.class,
                CommonImgEffectActivity.class,
                };

        ArrayList<Map<String, Object>> mData = new ArrayList<>();

        for (int i = 0; i < mListTitle.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("Title", mListTitle[i]);
            item.put("Text", mListStr[i]);
            mData.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mData,
                android.R.layout.simple_list_item_2, new String[] { "Title",
                        "Text" }, new int[] { android.R.id.text1,
                        android.R.id.text2 });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                    int position, long id) {
                Intent intent = new Intent(MainActivity.this,
                        mActivityList[position]);
                startActivity(intent);
            }
        });
    }
}
