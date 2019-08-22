package top.zcwfeng.flowlayout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class TagFlowLayoutAdapter {

    public abstract View createView(LayoutInflater inflater, ViewGroup parent,int position);
    public abstract int getItemCount();
    public abstract void bindView(View viewholder,int positon);

    public void onItemViewClick(View view,int position) {

    }

    public  void tipForSelectMax(View view, int mMaxSelectedCount){

    }

    public void onItemSelecte(View view,int position){

    }

    public void onItemUnSelecte(View view,int position){

    }

    public static interface OnDataChangedListener{
        void onDataChanged();
    }

    public OnDataChangedListener mListener;

    public void setOnDataChangedListener(OnDataChangedListener listener){
        mListener = listener;
    }

    public void onDataNotifyChanged(){
        if(mListener != null) mListener.onDataChanged();
    }
}
