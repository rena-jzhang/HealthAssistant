package com.example.finalproject.Activity;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.finalproject.Download.DownloadItem;
import com.example.finalproject.Download.DownloadService;
import com.example.finalproject.Download.downloadAdapter;
import com.example.finalproject.R;

public class DownloadList extends AppCompatActivity {

    public DownloadService.DownloadBinder downloadBinder;

    private List<DownloadItem> VideoList = new ArrayList<DownloadItem>();
    //    Object[] names;
    downloadAdapter videoAdapter;
    ListView listView;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }
    };

    //界面跳转方法
    public void showNextPage(Context packageContext, Class<?> cls) {
        // 在这里跳到第二个页面
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);

        //添加downloadItems
        initVideos();

        Toolbar toolbar=findViewById(R.id.toolbar3);
        toolbar.setTitle("运动视频");
        setSupportActionBar(toolbar);
        // 显示标题和子标题
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextPage(DownloadList.this,MainPage.class);
            }
        });

        //创建适配器并连接
        videoAdapter=new downloadAdapter(this,R.layout.download_item,VideoList,downloadBinder);
        listView = (ListView) findViewById(R.id.dld_list_view);
        listView.setAdapter(videoAdapter);

       // Intent intent = new Intent(DownloadList.this, DownloadService.class);
        //startService(intent); // 启动服务
        //bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务
        if (ContextCompat.checkSelfPermission(DownloadList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadList.this, new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


    }

    private void initVideos() {
        for (int i = 0; i < 4; i++) {
            DownloadItem a = new DownloadItem("FIRST", R.drawable.pic2,"http://jinxuliang.com/course/android/RuntimePermission/PPT.pdf");
            VideoList.add(a);
            DownloadItem b = new DownloadItem("SECOND", R.drawable.pic2,"http://jinxuliang.com/course/android/RuntimePermission/PPT.pdf");
            VideoList.add(b);
            DownloadItem c = new DownloadItem("THIRD", R.drawable.pic2,"http://jinxuliang.com/course/android/RuntimePermission/PPT.pdf");
            VideoList.add(c);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.
                        PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).
                            show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
 }

}