package top.zcwfeng.customui.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import top.zcwfeng.customui.R;


public class LayoutActivity extends AppCompatActivity {

    public static void LayoutActivityInstance(Context context){
        Intent intent = new Intent(context,LayoutActivity.class);
        context.startActivity(intent);
    }

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncLayoutInflater(this).inflate(R.layout.activity_layout, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                setContentView(view);
                rv = findViewById(R.id.rv);
                rv.setLayoutManager(new LinearLayoutManager(LayoutActivity.this));
                rv.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        return null;
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                    }

                    @Override
                    public int getItemCount() {
                        return 0;
                    }
                });
            }
        });

    }
}
