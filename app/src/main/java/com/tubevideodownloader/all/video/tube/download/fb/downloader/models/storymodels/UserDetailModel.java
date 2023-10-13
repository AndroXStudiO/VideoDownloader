package com.tubevideodownloader.all.video.tube.download.fb.downloader.models.storymodels;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class UserDetailModel implements Serializable {

    @SerializedName("user")
    private ModelUserInstagram modelUserInstagram;

    public ModelUserInstagram getModelUserInstagram() {
        return modelUserInstagram;
    }

    public void setModelUserInstagram(ModelUserInstagram modelUserInstagram) {
        this.modelUserInstagram = modelUserInstagram;
    }
}
