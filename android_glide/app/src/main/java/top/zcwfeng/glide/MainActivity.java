package top.zcwfeng.glide;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    public SparseArray array = new SparseArray();
    RecyclerView mRecyclerView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3806518612,303720472&fm=26&gp=0.jpg";
        ImageView imageView = (ImageView) findViewById(R.id.activity_img);
        Glide.with(this).load(url).into(imageView);
        array.append(1,url);
        array.append(2,url);
        mRecyclerView = findViewById(R.id.pic_recyclerview);
        adapter = new MyAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);


    }

    class MyViewHoler extends RecyclerView.ViewHolder{

        @BindView(R.id.rec_img)
        ImageView mImageView;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


    class MyAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view  = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_image,parent,false);
            return new MyViewHoler(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String url = (String) array.valueAt(position);
            MyViewHoler mHolder = (MyViewHoler) holder;

            Glide.with(MainActivity.this)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mHolder.mImageView);
        }


        @Override
        public int getItemCount() {
            return array.size();
        }
    }
}