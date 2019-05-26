package com.example.finalproject.Fragment.FoodListFrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.Fragment.FoodListFrag.FoodList;
import com.example.finalproject.Fragment.FoodListFrag.SavedFoodAdapter;
import com.example.finalproject.R;

public class LunchList extends FoodList {

    private String title;
    SavedFoodAdapter foodAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_list, container, false);
        if(fList.size()==0) ;
        TextView textView=view.findViewById(R.id.title_bar);
        textView.setText(title);
        //创建适配器并连接
        foodAdapter = new SavedFoodAdapter(this.getContext(), R.layout.food_item, fList);
        listView = (ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(foodAdapter);
        setListViewHeight(listView);
        foodAdapter.notifyDataSetChanged();
        //单击添加卡路里
        return view;
    }
    //为listview动态设置高度（有多少条目就显示多少条目）
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0,len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *  (listAdapter .getCount() - 1));
        listView.setLayoutParams(params);
    }
}
