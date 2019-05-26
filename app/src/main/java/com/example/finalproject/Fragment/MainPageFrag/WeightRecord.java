package com.example.finalproject.Fragment.MainPageFrag;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.Module.CircleProgress;
import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;

//import android.support.v4.app.Fragment;
public class WeightRecord extends Fragment {

    //全局变量引用
    User test = myStatic.getInstance().getTester();
    private CircleProgress mCircleProgress2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight, container, false);

        float progress=100;
        if(test.getWeight_record()!=test.getWeightTarget())
        {
            progress=(test.getWeight_record()-test.getWeight())*100/(test.getWeight_record()-test.getWeightTarget());
            if(progress<0) progress=0;
        }
        test.setProgress(progress);
        mCircleProgress2 = (CircleProgress) view.findViewById(R.id.circle_progress_bar2);
        mCircleProgress2.setValue(test.getProgress());
        TextView textView=(TextView)view.findViewById(R.id.cur_weight);
        textView.setText(test.getWeight()+"");
        TextView textView2=(TextView)view.findViewById(R.id.target_weight);
        textView2.setText(test.getWeightTarget()+"");

        return view;
    }
}