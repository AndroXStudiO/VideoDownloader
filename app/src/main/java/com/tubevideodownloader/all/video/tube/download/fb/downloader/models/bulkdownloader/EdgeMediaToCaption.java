package com.tubevideodownloader.all.video.tube.download.fb.downloader.models.bulkdownloader;

import androidx.annotation.Keep;

import java.util.List;

@Keep
public class EdgeMediaToCaption {
    List<EdgeInfo> edges;

    public List<EdgeInfo> getEdges() {
        return this.edges;
    }

    public void setEdges(List<EdgeInfo> edges) {
        this.edges = edges;
    }
}