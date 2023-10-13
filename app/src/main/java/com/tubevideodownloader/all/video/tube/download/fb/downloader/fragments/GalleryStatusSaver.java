/*
 * *
 *  * Created by Syed Usama Ahmad on 3/2/23, 7:47 PM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 3/2/23, 5:58 PM
 *
 */

package com.tubevideodownloader.all.video.tube.download.fb.downloader.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tubevideodownloader.all.video.tube.download.fb.downloader.R;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.adapters.GalleryAdapter;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.databinding.FragmentStatusSaverGalleryBinding;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.models.StatusSaverGalleryModel;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.utils.Constants;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.utils.FilePathUtility;
import com.tubevideodownloader.all.video.tube.download.fb.downloader.utils.iUtils;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class GalleryStatusSaver extends Fragment {

    public ArrayList<StatusSaverGalleryModel> statusSaverGalleryModelArrayList;
    AsyncTask<Void, Void, Void> fetchRecordingsAsyncTask;
    private FragmentStatusSaverGalleryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remember add this line
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStatusSaverGalleryBinding.inflate(inflater, container, false);
        statusSaverGalleryModelArrayList = new ArrayList<>();
        initViews();

//        fetchRecordingsAsyncTask = new FetchRecordingsAsyncTask(getActivity());
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
            getAllFiles();
            binding.swiperefreshlayout.setRefreshing(false);
        });
    }

    private void getAllFiles() {
        try {
            statusSaverGalleryModelArrayList = new ArrayList<>();


            String location = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;

            File[] files = new File(location).listFiles();


            if (files != null && files.length > 1) {
                Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            }
            if (files != null) {
                for (int i = 0; i < files.length; i++) {

                    statusSaverGalleryModelArrayList.add(new StatusSaverGalleryModel(getString(R.string.savedstt) + i, Uri.fromFile(files[i]), files[i].getAbsolutePath(), files[i].getName()));
                }

                setAdapterRecyclerView();
            } else {
                requireActivity().runOnUiThread(() -> binding.noresultfound.setVisibility(View.VISIBLE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void setAdapterRecyclerView() {

        GalleryAdapter fileListAdapter = new GalleryAdapter(getActivity(), statusSaverGalleryModelArrayList, path -> {
            if (path == null) {
                requireActivity().runOnUiThread(() -> {
                    iUtils.ShowToastError(requireActivity(), requireActivity().getString(R.string.error_occ)
                    );
                });
            } else {
                FilePathUtility.deleteAndroid10andABOVE(requireActivity(), path, "v");

            }
        });
        binding.recststuslist.setAdapter(fileListAdapter);

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
            setAdapterRecyclerView();
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
                if (fetchRecordingsAsyncTask != null) {
                    fetchRecordingsAsyncTask.cancel(true);
                }
            }
        }


    }

}