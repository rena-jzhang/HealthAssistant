package com.example.finalproject.Fragment.FoodListFrag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.List;

public class SavedFoodAdapter extends ArrayAdapter<Food>{

        private int resourceId;
        //存放控件
        class ViewHolder
        {
            TextView foodName2;
            TextView foodCal2;
            ImageView foodLevel2;
        }

        public SavedFoodAdapter(Context context, int textViewResourceId, List<Food> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        //这里的convertview是一个缓存参数
        public View getView(int position, View convertView, ViewGroup parent) {
            Food food = getItem(position); // 获取当前项的Food实例
            View view ;
            ViewHolder viewHolder;
            if(convertView==null)
            {
                view= LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
                //如果当前行没有被加载过， 那么创建一个viewholder，放入一个当前刚创建的view实体，也就是一个当前行

                viewHolder=new com.example.finalproject.Fragment.FoodListFrag.SavedFoodAdapter.ViewHolder();
                viewHolder.foodName2 = (TextView) view.findViewById(R.id.food_name);
                viewHolder.foodCal2=(TextView) view.findViewById(R.id.food_cal);
                viewHolder.foodLevel2=(ImageView) view.findViewById(R.id.food_image);
                view.setTag(viewHolder);         //讲viewholder储存在view 里面
            }
            else
            {

                view=convertView;
                viewHolder=(com.example.finalproject.Fragment.FoodListFrag.SavedFoodAdapter.ViewHolder)view.getTag();

            }
            viewHolder.foodLevel2.setImageResource(food.getPicId());
            viewHolder.foodName2.setText(food.getName());
            viewHolder.foodCal2.setText(food.getAmt()+"g");

            ImageView foodLevel = (ImageView) view.findViewById(R.id.food_image);
            TextView foodName = (TextView) view.findViewById(R.id.food_name);
            TextView foodCal = (TextView) view.findViewById(R.id.food_cal);
            foodLevel.setImageResource(food.getPicId());
            foodName.setText(food.getName());
            foodCal.setText(food.getAmt()+"g");
            return view;
        }

}
