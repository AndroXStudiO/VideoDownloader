/*
 * *
 *  * Created by Syed Usama Ahmad on 1/31/23, 11:56 PM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 1/31/23, 11:53 PM
 *
 */

package com.tubevideodownloader.all.video.tube.download.fb.downloader.interfaces;


import com.tubevideodownloader.all.video.tube.download.fb.downloader.models.storymodels.ModelHighlightsUsrTray;

public interface HighlightsListInStoryListner {
    void onclickUserHighlightsListItem(int position, ModelHighlightsUsrTray modelUsrTray);
}