package com.example.finalproject.Download;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadItem {

    private int picId;
    private int btn_start_Id;
    private int btn_cancel_Id;
    private String title;

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    private String downloadURL;

    public DownloadItem(String s,int pic,String downloadURL)
    {
        this.picId=pic;
        this.title=s;
        this.downloadURL=downloadURL;
    }

    public int getBtn_start_Id() {
        return btn_start_Id;
    }

    public int getBtn_cancel_Id() {
        return btn_cancel_Id;
    }

    public String getTitle() {
        return title;
    }

    public int getPicId() {
        return this.picId;
    }
}
