package com.example.finalproject.Download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.finalproject.R;

import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.BIND_AUTO_CREATE;

public class downloadAdapter extends ArrayAdapter<DownloadItem> {
    private int resourceId;
    public DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            downloadBinder = (DownloadService.DownloadBinder) service;
            if(downloadBinder==null) Log.d(TAG, "onServiceConnected:null ");
        }
    };
    //存放控件
    class ViewHolder {

        Button btn_start;
        Button btn_cancel;
        ImageView pagePic;
        TextView title;

    }

    public downloadAdapter(Context context, int textViewResourceId, List<DownloadItem> objects, DownloadService.DownloadBinder binder) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    //这里的convertview是一个缓存参数
    public View getView(int position, View convertView, ViewGroup parent) {

        final DownloadItem dld = getItem(position); // 获取当前项的ITEM实例
        final View view;
        final ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            //如果当前行没有被加载过， 那么创建一个viewholder，放入一个当前刚创建的view实体，也就是一个当前行
            viewHolder = new ViewHolder();
            viewHolder.pagePic = (ImageView) view.findViewById(R.id.video_page);
            viewHolder.title = (TextView) view.findViewById(R.id.video_title);
            viewHolder.btn_start = (Button) view.findViewById(R.id.start_download);
            viewHolder.btn_cancel = (Button) view.findViewById(R.id.cancel_download);
            view.setTag(viewHolder);         //讲viewholder储存在view 里面
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.pagePic.setImageResource(dld.getPicId());
        viewHolder.title.setText(dld.getTitle());
        viewHolder.btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: binder");
                 Intent intent = new Intent(view.getContext(), DownloadService.class);
                view.getContext().startService(intent); // 启动服务
                view.getContext().bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务
              if(downloadBinder==null) return;
              else downloadBinder.startDownload(dld.getDownloadURL());
            }
        });
        viewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), DownloadService.class);
                view.getContext().startService(intent); // 启动服务
                view.getContext().bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务
                if(downloadBinder==null) return;
                else downloadBinder.cancelDownload();
            }
        });

        return view;
    }


}
