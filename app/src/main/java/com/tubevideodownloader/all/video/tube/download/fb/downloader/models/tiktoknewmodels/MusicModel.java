package com.tubevideodownloader.all.video.tube.download.fb.downloader.models.tiktoknewmodels;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Keep
public class MusicModel implements Serializable {
    @SerializedName("title")
    public String title;

    public MusicModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}