package com.bumble.Utils.filepicker.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumble.Utils.Restring;
import com.bumble.Utils.filepicker.Constant;
import com.bumble.Utils.filepicker.DividerGridItemDecoration;
import com.bumble.Utils.filepicker.adapter.ImageDirectoryAdapter;
import com.bumble.Utils.filepicker.adapter.ImagePickAdapter;
import com.bumble.Utils.filepicker.adapter.OnSelectStateListener;
import com.bumble.Utils.filepicker.filter.FileFilter;
import com.bumble.Utils.filepicker.filter.callback.FilterResultCallback;
import com.bumble.Utils.filepicker.filter.entity.Directory;
import com.bumble.Utils.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bumblesdk.R;

/**
 * Created by Vincent Woo
 * Date: 2016/10/12
 * Time: 16:41
 */

public class ImagePickActivity extends BaseActivity {
    public static final String IS_NEED_CAMERA = "IsNeedCamera";
    public static final String IS_NEED_IMAGE_PAGER = "IsNeedImagePager";
    public static final String IS_TAKEN_AUTO_SELECTED = "IsTakenAutoSelected";

    public static final int DEFAULT_MAX_NUMBER = 1;
    private int mMaxNumber;
    private int mCurrentNumber = 0;
    private RecyclerView mRecyclerView;
    private ImagePickAdapter mAdapter;
    private ImageDirectoryAdapter directoryAdapter;
    private boolean isNeedCamera;
    private boolean isNeedImagePager;
    private boolean isTakenAutoSelected;
    public ArrayList<ImageFile> mSelectedList = new ArrayList<>();

    private GridLayoutManager layoutManager;
    private boolean isDirectory;
    private Toolbar myToolbar;

    @Override
    public void permissionGranted() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vw_activity_image_pick);

        mMaxNumber = getIntent().getIntExtra(Constant.MAX_NUMBER, DEFAULT_MAX_NUMBER);
        isNeedCamera = getIntent().getBooleanExtra(IS_NEED_CAMERA, false);
        isNeedImagePager = getIntent().getBooleanExtra(IS_NEED_IMAGE_PAGER, true);
        isTakenAutoSelected = getIntent().getBooleanExtra(IS_TAKEN_AUTO_SELECTED, true);
        initView();
    }

    private void initView() {
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        String title = Restring.getString(ImagePickActivity.this, R.string.hippo_image_picker);
        setToolbar(myToolbar, title);

        mRecyclerView = findViewById(R.id.rv_image_pick);
        layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mAdapter = new ImagePickAdapter(this, isNeedCamera, isNeedImagePager, mMaxNumber);
        directoryAdapter = new ImageDirectoryAdapter(this);
        mRecyclerView.setAdapter(directoryAdapter);

        directoryAdapter.setOnSelectStateListener(new OnSelectStateListener<Directory>() {
            @Override
            public void OnSelectStateChanged(boolean state, Directory file) {
                isDirectory = true;
                layoutManager.setSpanCount(3);
                mRecyclerView.setLayoutManager(layoutManager);
                List<ImageFile> list = new ArrayList<>();
                list.addAll(file.getFiles());
                mAdapter.refresh(list);
                mRecyclerView.setAdapter(mAdapter);
                runLayoutAnimation(mRecyclerView);
            }
        });

        mAdapter.setOnSelectStateListener(new OnSelectStateListener<ImageFile>() {
            @Override
            public void OnSelectStateChanged(boolean state, ImageFile file) {
                mSelectedList.add(file);
                mCurrentNumber++;
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE, mSelectedList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_TAKE_IMAGE:
                if (resultCode == RESULT_OK) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File file = new File(mAdapter.mImagePath);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);

                    loadData();
                } else {
                    //Delete the record in Media DB, when user select "Cancel" during take picture
                    getApplicationContext().getContentResolver().delete(mAdapter.mImageUri, null, null);
                }
                break;
            case Constant.REQUEST_CODE_BROWSER_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_BROWSER_IMAGE);
                    mCurrentNumber = list.size();
                    mAdapter.setCurrentNumber(mCurrentNumber);
                    mSelectedList.clear();
                    mSelectedList.addAll(list);

                    for (ImageFile file : mAdapter.getDataSet()) {
                        if (mSelectedList.contains(file)) {
                            file.setSelected(true);
                        } else {
                            file.setSelected(false);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void loadData() {
        FileFilter.getImages(this, new FilterResultCallback<ImageFile>() {
            @Override
            public void onResult(List<Directory<ImageFile>> directories) {
                // Refresh folder list
                if (isNeedFolderList) {
                    ArrayList<Directory> list = new ArrayList<>();
                    Directory all = new Directory();
                    all.setName(getResources().getString(R.string.vw_all));
                    list.add(all);
                    list.addAll(directories);
                    mFolderHelper.fillData(list);
                }

//                mAll = directories;
                refreshData(directories);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isDirectory) {
            isDirectory = false;
            layoutManager.setSpanCount(2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(directoryAdapter);
        } else {
            super.onBackPressed();
        }
    }

    private void refreshData(List<Directory<ImageFile>> directories) {
        List<Directory> directoryList = new ArrayList<>();
        directoryList.addAll(directories);
        directoryAdapter.refresh(directoryList);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.hippo_layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
