package com.tubevideodownloader.all.video.tube.download.fb.downloader.models.dlapismodels;

import androidx.annotation.Keep;

@Keep
public class FragmentModelVideo {
    private String path;
    private Double duration;

    public String getPath() {
        return path;
    }

    public void setPath(String value) {
        this.path = value;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double value) {
        this.duration = value;
    }


}