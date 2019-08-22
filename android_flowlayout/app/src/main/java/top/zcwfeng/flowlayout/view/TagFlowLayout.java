package top.zcwfeng.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import top.zcwfeng.flowlayout.adapter.TagFlowLayoutAdapter;

public class TagFlowLayout extends Flowlayout implements TagFlowLayoutAdapter.OnDataChangedListener {
    private TagFlowLayoutAdapter mAdapter;
    private int mMaxSelectedCount;

    public void setMaxSelectedCount(int count)
    {
        mMaxSelectedCount = count;
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmAdapter(TagFlowLayoutAdapter mAdapter){
        this.mAdapter = mAdapter;
        mAdapter.setOnDataChangedListener(this);
        onDataChanged();
    }

    @Override
    public void onDataChanged() {
        removeAllViews();
        for(int i = 0; i< mAdapter.getItemCount(); i++) {
            View view  = mAdapter.createView(LayoutInflater.from(getContext()),this,i);
            mAdapter.bindView(view,i);
            addView(view);

            if(view.isSelected()) {
                mAdapter.onItemSelecte(view,i);
            } else {
                mAdapter.onItemUnSelecte(view,i);
            }

            bindMethod(i,view);
        }
    }

    /**
     * 单选，选择完，其他的不能选中，选择下一个上一个取消
     * 多选，最多能选中n个，需要手动反选
     * @param position
     * @param view
     */
    private void bindMethod(final int position, final View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.onItemViewClick(v,position);

                if(mMaxSelectedCount <= 0) return;

                // 特殊
                if(!view.isSelected()) {
                    if(getSelectViewCount() >= mMaxSelectedCount) {
                        if(getSelectViewCount() == 1) {
                            //单选
                            View selectView = getSelectView();
                            if(selectView != null) {
                                selectView.setSelected(false);
                                mAdapter.onItemUnSelecte(selectView,getPositionForSelectView(selectView));
                            }
                        }else {
                            //多选
                            mAdapter.tipForSelectMax(v,mMaxSelectedCount);
                            return;
                        }
                    }
                }


                if(view.isSelected()) {
                    view.setSelected(false);
                    mAdapter.onItemUnSelecte(view,getPositionForSelectView(view));

                }else {
                    view.setSelected(true);
                    mAdapter.onItemSelecte(view,getPositionForSelectView(view ));

                }
            }
        });
    }

    public List<Integer> getSelectedItemPositionList(){
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);

            if(view.isSelected()) {
                list.add(i);
            }
        }

        return list;
    }

    private int getPositionForSelectView(View selectview){
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(view == selectview) {
                return i;
            }
        }
        return 0;
    }

    private View getSelectView() {
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(view.isSelected()) {
                return view;
            }
        }
        return null;
    }

    private int getSelectViewCount() {
        int result = 0;
        for(int i=0;i<getChildCount();i++) {
            View view = getChildAt(i);
            if(view.isSelected()) {
                result ++;
            }
        }
        return result;
    }


}
