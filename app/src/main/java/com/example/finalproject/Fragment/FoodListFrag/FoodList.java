package com.example.finalproject.Fragment.FoodListFrag;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;


public class FoodList extends Fragment {


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    public List<Food> fList=new ArrayList<Food>();
    FoodAdapter foodAdapter;
    private ListView listView;


    public List<Food> getfList() {return fList; }
    public void setfList(List<Food> fList) { this.fList = fList; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.only_food_list, container, false);
        if(fList.size()==0)
        initFoods();

        //创建适配器并连接
        foodAdapter = new FoodAdapter(this.getContext(), R.layout.food_item, fList);
        listView = (ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(foodAdapter);
        //单击添加卡路里
        return view;
    }


    private void initFoods() {
            title="请添加食物";
            for(int i=0;i<3;i++)
            {
                Food food=new Food(123,"apple",R.drawable.greendot,0);
                fList.add(food);
                Food food1=new Food(223,"chip",R.drawable.yellow,0);
                fList.add(food1);
                Food food2=new Food(345,"chocolate",R.drawable.red,0);
                fList.add(food2);
            }
    }


}