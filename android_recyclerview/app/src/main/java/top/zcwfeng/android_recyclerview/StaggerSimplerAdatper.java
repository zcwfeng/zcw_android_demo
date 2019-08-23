package top.zcwfeng.android_recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StaggerSimplerAdatper extends SimplerAdatper {

    private List<Integer> mHeights;

    public StaggerSimplerAdatper(Context context, List<String> datas) {
        super(context,datas);
        mHeights = new ArrayList<>();
        for(int i=0;i<mDatas.size();i++){
            mHeights.add((int) (100 + Math.random()*300));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.itemView.setLayoutParams(lp);
        holder.mTv.setText(mDatas.get(position));

        setUpOnItemEvent(holder);
    }


}
