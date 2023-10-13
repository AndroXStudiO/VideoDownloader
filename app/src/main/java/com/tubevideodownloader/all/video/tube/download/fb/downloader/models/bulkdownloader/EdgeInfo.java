package com.tubevideodownloader.all.video.tube.download.fb.downloader.models.bulkdownloader;

import androidx.annotation.Keep;

@Keep
public class EdgeInfo {
    NodeInfo node;

    public NodeInfo getNode() {
        return this.node;
    }

    public void setNode(NodeInfo node) {
        this.node = node;
    }
}