package com.bumble.Utils.filepicker.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumble.BumbleConfig;
import com.bumble.Utils.Restring;
import com.bumble.Utils.filepicker.Constant;
import com.bumble.Utils.filepicker.DividerListItemDecoration;
import com.bumble.Utils.filepicker.FileUtils;
import com.bumble.Utils.filepicker.Util;
import com.bumble.Utils.filepicker.adapter.NormalFilePickAdapter;
import com.bumble.Utils.filepicker.adapter.OnSelectStateListener;
import com.bumble.Utils.filepicker.filter.FileFilter;
import com.bumble.Utils.filepicker.filter.callback.FilterResultCallback;
import com.bumble.Utils.filepicker.filter.entity.Directory;
import com.bumble.Utils.filepicker.filter.entity.NormalFile;
import com.bumblesdk.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Vincent Woo
 * Date: 2016/10/26
 * Time: 10:14
 */

public class NormalFilePickActivity extends BaseActivity {

    public static String id1 = "test_channel_01";
    private static final String TAG = NormalFilePickActivity.class.getSimpleName();
    public static final int DEFAULT_MAX_NUMBER = 9;
    private static final int REQUEST_CODE = 6384;
    public static final String SUFFIX = "Suffix";
    private int mMaxNumber;
    private RecyclerView mRecyclerView;
    private NormalFilePickAdapter mAdapter;
    private ArrayList<NormalFile> mSelectedList = new ArrayList<>();
    private ProgressBar mProgressBar;
    private String[] mSuffix;
    private Toolbar myToolbar;

    @Override
    public void permissionGranted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        }, 2000);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vw_activity_image_pick);

        mMaxNumber = getIntent().getIntExtra(Constant.MAX_NUMBER, DEFAULT_MAX_NUMBER);
        mSuffix = getIntent().getStringArrayExtra(SUFFIX);
        initView();
    }

    private void initView() {

        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        String title = Restring.getString(NormalFilePickActivity.this, R.string.hippo_doc_picker);
        setToolbar(myToolbar, title);
        mProgressBar = findViewById(R.id.pb_file_pick);
        mProgressBar.setVisibility(View.VISIBLE);

        mRecyclerView = findViewById(R.id.rv_image_pick);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this,
                LinearLayoutManager.VERTICAL, R.drawable.vw_divider_rv_file));
        mAdapter = new NormalFilePickAdapter(this, mMaxNumber);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnSelectStateListener(new OnSelectStateListener<NormalFile>() {
            @Override
            public void OnSelectStateChanged(boolean state, NormalFile file) {
                if (file == null) {
                    openFileManager();
                } else {
                    mSelectedList.add(file);
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(Constant.RESULT_PICK_FILE, mSelectedList);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


    }

    private void loadData() {
        FileFilter.getFiles(this, new FilterResultCallback<NormalFile>() {
            @Override
            public void onResult(List<Directory<NormalFile>> directories) {
                // Refresh folder list
                if (isNeedFolderList) {
                    ArrayList<Directory> list = new ArrayList<>();
                    Directory all = new Directory();
                    all.setName(getResources().getString(R.string.vw_all));
                    list.add(all);
                    list.addAll(directories);
                    mFolderHelper.fillData(list);
                }
                refreshData(directories);
            }
        }, mSuffix);
    }

    private void refreshData(List<Directory<NormalFile>> directories) {
        mProgressBar.setVisibility(View.GONE);
        List<NormalFile> list = new ArrayList<>();
        for (Directory<NormalFile> directory : directories) {
            list.addAll(directory.getFiles());
        }

        for (NormalFile file : mSelectedList) {
            int index = list.indexOf(file);
            if (index != -1) {
                list.get(index).setSelected(true);
            }
        }

        mAdapter.refresh(list);
    }


    private void openFileManager() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                final Uri uri = data.getData();
                final String path = FileUtils.getPath(this, uri);
                NormalFile file = new NormalFile();
                String fileName = Util.extractFileNameWithoutSuffix(path);
                file.setPath(path);
                file.setName(fileName);
                file.setSize(new File(path).length());

                mSelectedList.add(file);
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(Constant.RESULT_PICK_FILE, mSelectedList);
                setResult(RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                if (BumbleConfig.DEBUG)
                    e.printStackTrace();
            }
        }
    }

    /**
     * for API 26+ create notification channels
     */
    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel mChannel = new NotificationChannel(id1,
                    ("Testing"),  //name of the channel
                    NotificationManager.IMPORTANCE_LOW);   //importance level
            //important level: default is is high on the phone.  high is urgent on the phone.  low is medium, so none is low?
            // Configure the notification channel.
            mChannel.setDescription("Testing description");
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this channel, if the device supports this feature.
            mChannel.setShowBadge(true);
            nm.createNotificationChannel(mChannel);
        }
    }

}
