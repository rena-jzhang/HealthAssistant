package com.example.finalproject.Fragment.MainPageFrag;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject.Activity.DownloadList;
import com.example.finalproject.Module.PopupWindow.BottomPopupOption;
import com.example.finalproject.R;

import static android.content.ContentValues.TAG;


//import android.support.v4.app.Fragment;
public class ExerciseRecord extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.exercise_record, container, false);
        Button button=(Button)view.findViewById(R.id.to_download_page);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
              //  func2();
            }
        });
        return view;
    }

    //创建回调接口
    public static interface onCallBackListener2 {
        public void toDownloadPage();
    }


    public void setCallBackListener(onCallBackListener2 callBackListener) {
        this.callBackListener = callBackListener;
    }

    //声明接口对象
    private onCallBackListener2 callBackListener;

    private void func2() {
        if (callBackListener != null)
        {
            Log.d(TAG, "func: ");
            callBackListener.toDownloadPage();
        }
    }
}