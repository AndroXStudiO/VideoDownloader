package com.tubevideodownloader.all.video.tube.download.fb.downloader.models.bulkdownloader;


import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class Graphql implements Serializable {
    UserInfoInsta user;

    @SerializedName("user")
    public UserInfoInsta getUser() {
        return this.user;
    }

    public void setUser(UserInfoInsta user) {
        this.user = user;
    }
}