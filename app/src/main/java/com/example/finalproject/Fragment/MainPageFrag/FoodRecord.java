package com.example.finalproject.Fragment.MainPageFrag;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;

import static android.content.ContentValues.TAG;


//import android.support.v4.app.Fragment;
public class FoodRecord extends Fragment {

    //全局变量引用
    User test = myStatic.getInstance().getTester();
    private int calLeft;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_record, container, false);
        calLeft=test.getTargetCal()-test.getLunchCal()-test.getDinnerCal()-test.getBreakfastCal();
        TextView textView=view.findViewById(R.id.cal_left);
        textView.setText(calLeft+"");
        Log.d(TAG, "calleft: "+" "+test.getBreakfastCal()+" "+test.getLunchCal());
        ProgressBar breakfastBar=view.findViewById(R.id.breakfastBar);
        breakfastBar.setProgress(test.getBreakfastCal()*100/test.getBreakTarget());
        ProgressBar lunchBar=view.findViewById(R.id.lunchBar);
        lunchBar.setProgress(test.getLunchCal()*100/test.getLunchTarget());
        ProgressBar dinnerBar=view.findViewById(R.id.dinnerBar);
        dinnerBar.setProgress(test.getDinnerCal()*100/test.getDinnerTarget());
        return view;
    }
}