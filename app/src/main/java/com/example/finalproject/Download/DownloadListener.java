package com.example.finalproject.Download;

/**
 * Created by JinXuLiang on 2017/11/5.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
