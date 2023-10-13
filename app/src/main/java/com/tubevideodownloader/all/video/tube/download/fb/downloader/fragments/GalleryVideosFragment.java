/*
 * *
 *  * Created by Syed Usama Ahmad on 3/3/23, 12:53 AM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 3/2/23, 11:57 PM
 *
 */

package com.tubevideodownloader.all.video.tube.download.fb.downloader.fragments;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.tubevideodownloader.all.video.tube.download.fb.downloader.utils.Constants.directoryInstaShoryDirectorydownload_videos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tubevideodownloader.all.video.tube.download.fb.downloader.R;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.adapters.InstagramImageFileListAdapter;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.databinding.FragmentInstagalleryImagesBinding;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.utils.FilePathUtility;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.utils.iUtils;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GalleryVideosFragment extends Fragment {
    AsyncTask<Void, Void, Void> fetchRecordingsAsyncTask;
    private InstagramImageFileListAdapter instagramImageFileListAdapter;
    private ArrayList<File> fileArrayList;
    private FragmentInstagalleryImagesBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remember add this line
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInstagalleryImagesBinding.inflate(inflater, container, false);

        fileArrayList = new ArrayList<>();
        initViews();

//        fetchRecordingsAsyncTask = new FetchRecordingsAsyncTask(requireActivity());
//        fetchRecordingsAsyncTask.execute();

        try {
            getAllFiles();
        } catch (Exception e) {
            requireActivity().runOnUiThread(() -> {
                iUtils.ShowToastError(requireActivity(), requireActivity().getString(R.string.error_occ)
                );
            });
            e.printStackTrace();
        }

        return binding.getRoot();
    }


    private void initViews() {


        binding.swiperefreshlayout.setOnRefreshListener(() -> {

            fileArrayList = new ArrayList<>();
            getAllFiles();
            binding.swiperefreshlayout.setRefreshing(false);
        });
    }

    private void getAllFiles() {
        try {
            fileArrayList = new ArrayList<>();
            String location = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + directoryInstaShoryDirectorydownload_videos;

            File[] files = new File(location).listFiles();

            if (files != null && files.length > 1) {
                Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            }
            if (files != null) {
                for (File file : files) {
                    if (file.getAbsolutePath().endsWith(".mp4") || file.getAbsolutePath().endsWith(".mkv") || file.getAbsolutePath().endsWith(".webm")) {
                        fileArrayList.add(file);
                    }
                }

                setAdaptertoRecyclerView();
            } else {

                requireActivity().runOnUiThread(() -> binding.noresultfound.setVisibility(View.VISIBLE));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private final ActivityResultLauncher<IntentSenderRequest> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    requireActivity().runOnUiThread(() -> {
                        iUtils.ShowToastError(requireActivity(), requireActivity().getString(R.string.file_del)
                        );
                    });
                }
            });


    void setAdaptertoRecyclerView() {
        requireActivity().runOnUiThread(() -> {
            instagramImageFileListAdapter = new InstagramImageFileListAdapter(requireActivity(), fileArrayList, path -> {
                if (path == null) {
                    requireActivity().runOnUiThread(() -> {
                        iUtils.ShowToastError(requireActivity(), requireActivity().getString(R.string.error_occ)
                        );
                    });
                } else {
                    FilePathUtility.deleteAndroid10andABOVE(requireActivity(), path, "v");
                }


            });
            binding.recviewInstaImage.setAdapter(instagramImageFileListAdapter);
        });


    }


    private class FetchRecordingsAsyncTask extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog;

        public FetchRecordingsAsyncTask(Context activity) {
            dialog = new ProgressDialog(activity, R.style.AppTheme_Dark_Dialog);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getString(R.string.loadingdata));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... args) {
            getAllFiles();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            new Handler().postDelayed(() -> {
                setAdaptertoRecyclerView();
                // do UI work here
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    if (fetchRecordingsAsyncTask != null) {
                        fetchRecordingsAsyncTask.cancel(true);
                    }
                }
            }, 1000);

        }


    }


}