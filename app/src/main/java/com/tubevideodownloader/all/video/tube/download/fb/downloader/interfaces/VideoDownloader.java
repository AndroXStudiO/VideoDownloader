package com.tubevideodownloader.all.video.tube.download.fb.downloader.interfaces;

public interface VideoDownloader {

    String getVideoId(String link);

    void DownloadVideo();
}