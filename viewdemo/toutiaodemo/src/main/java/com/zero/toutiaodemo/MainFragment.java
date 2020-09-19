package com.zero.toutiaodemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;


public class MainFragment extends ListFragment {


    public static Fragment newIntance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    ArrayAdapter<String> arrayAdapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] array = new String[]{
                "文字测量",//0
                "OverDraw",//1
                "简单文字测量demo",//2
                "ViewPager+文字变色",//3
                "绘制测试",//4
        };
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        String item = arrayAdapter.getItem(position);
        Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
        Intent gotoAct;
        switch (position) {
            case 0://文字测量
                gotoAct = new Intent(getActivity(),TextMeasureActivity.class);
                startActivity(gotoAct);
                break;
            case 1://OverDraw
                gotoAct = new Intent(getActivity(),OverDrawActivity.class);
                startActivity(gotoAct);
                break;
            case 2://简单文字测量demo
                gotoAct = new Intent(getActivity(),SimpleActivity.class);
                startActivity(gotoAct);
                break;
            case 3://ViewPager+文字变色
                gotoAct = new Intent(getActivity(),ViewPagerActivity.class);
                startActivity(gotoAct);
                break;
            case 4://ViewPager+文字变色
                gotoAct = new Intent(getActivity(),DrawTestActivity.class);
                startActivity(gotoAct);
                break;
            default:
                break;
        }
    }


}
