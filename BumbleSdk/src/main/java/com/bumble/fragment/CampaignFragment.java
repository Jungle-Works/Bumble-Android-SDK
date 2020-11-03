package com.bumble.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumble.BumbleConfig;
import com.bumble.BumbleConfigAttributes;
import com.bumble.MobileCampaignBuilderBumble;
import com.bumble.NotificationListener;
import com.bumble.Utils.Restring;
import com.bumble.Utils.ToastUtil;
import com.bumble.Utils.swipetoshow.SwipeHolder;
import com.bumble.Utils.swipetoshow.SwipeOnItemTouchAdapter;
import com.bumble.adapter.CampaignAdapter;
import com.bumble.apis.ApiGetMobileNotification;
import com.bumble.database.CommonData;
import com.bumble.interfaces.OnClearNotificationListener;
import com.bumble.interfaces.OnItemClickListener;
import com.bumble.model.promotional.Data;
import com.bumble.model.promotional.PromotionResponse;
import com.google.gson.Gson;


import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashSet;

import com.bumblesdk.R;


public class CampaignFragment extends BaseFragment implements OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = CampaignFragment.class.getSimpleName();
    private View rootView;
    private TextView tvToolbarName;
    private RelativeLayout myToolbar;
    private TextView delete;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CampaignAdapter campaignAdapter;
    private MobileCampaignBuilderBumble campaignBuilder;
    private int startOffset = 0;
    private int offset = 20;
    //private int endOffset;
    private ArrayList<Data> arrayList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean hasMorePages;
    private boolean isPagingApiInProgress;
    private TextView titleError;
    private ImageView ivBackBtn;
    private LinearLayout llNoNotifications;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.hippo_campaigns_layout,  container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BumbleConfig.getInstance().setAnnouncementActivity(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BumbleConfig.getInstance().setAnnouncementActivity(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        campaignBuilder = BumbleConfig.getInstance().getMobileCampaignBuilder();

        refreshLayout = view.findViewById(R.id.swipe_refresh);
        titleError = view.findViewById(R.id.title_error);
        recyclerView = view.findViewById(R.id.recyclerView);
        //endOffset = offset;

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.hippo_white);
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.fugu_theme_color_primary);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

        llNoNotifications = view.findViewById(R.id.llNoNotifications);

        myToolbar = view.findViewById(R.id.my_toolbar);
        tvToolbarName = view.findViewById(R.id.tv_toolbar_name);
        String title = Restring.getString(getActivity(), R.string.hippo_notifications_title);
        String errorTxt = Restring.getString(getActivity(), R.string.hippo_no_notifications);
        if(campaignBuilder != null && !TextUtils.isEmpty(campaignBuilder.getNotificationTitle()))
            title = campaignBuilder.getNotificationTitle();

        if(campaignBuilder != null && !TextUtils.isEmpty(campaignBuilder.getEmptyNotificationText()))
            errorTxt = campaignBuilder.getEmptyNotificationText();

        tvToolbarName.setText(title);
        myToolbar.setBackgroundColor(CommonData.getColorConfig().getHippoActionBarBg());
        tvToolbarName.setTextColor(CommonData.getColorConfig().getHippoActionBarText());
        titleError.setText(errorTxt);
        delete = view.findViewById(R.id.deleteTxt);
        delete.setTextColor(CommonData.getColorConfig().getHippoActionBarText());

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        campaignAdapter = new CampaignAdapter(getActivity(), null, recyclerView, this);
        recyclerView.setAdapter(campaignAdapter);

        ivBackBtn = view.findViewById(R.id.ivBackBtn);
        ivBackBtn.getDrawable().setColorFilter(CommonData.getColorConfig().getHippoActionBarText(), PorterDuff.Mode.SRC_ATOP);

        setTextSize(tvToolbarName, 20);
        setTextSize(delete, 13.4f);
        setTextSize(titleError, 18);

        getSavedData();

        //CommonData.deleteAnnouncementCount();

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    visibleItemCount = layoutManager.getChildCount();
//                    totalItemCount = layoutManager.getItemCount();
//                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
//
//                    if (!isPagingApiInProgress && hasMorePages) {
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                            startOffset = arrayList.size();
//                            //endOffset = startOffset +offset;
//                            fetchData();
//                        }
//                    }
//                }
//            }
//        });

        recyclerView.addOnItemTouchListener(new SwipeOnItemTouchAdapter(getActivity(), recyclerView, layoutManager) {
            @Override
            public void onItemHiddenClick(SwipeHolder swipeHolder, int position) {
                clearData(arrayList.get(position).getChannelId(), position);
                //call reset to hide.
                swipeHolder.reset();
            }

            @Override
            public void onItemClick(int position) {

            }
        });

        delete.setText(Restring.getString(getActivity(), R.string.hippo_clear_all_notification));
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData(-1, -1);
            }
        });

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getActivity().onBackPressed();
                } catch (Exception e) {
                }
            }
        });
    }

    BumbleConfigAttributes attributes;

    private BumbleConfigAttributes getAttributes() {
        if(attributes == null)
            attributes = CommonData.getAttributes();
        return attributes;
    }

    public void setTextSize(TextView textView, float size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    @Override
    public void onRefresh() {
        if(isNetworkAvailable()) {
            startOffset = 0;
            //endOffset = startOffset + offset;
            fetchData();
        } else {
            refreshLayout.setRefreshing(false);
        }
    }

    public static JSONObject objectToJSONObject(Object object){
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }

    @Override
    public void onClickListener(int position) {
        try {
            if(campaignBuilder != null && campaignBuilder.getListener() != null) {
                String data = new Gson().toJson(arrayList.get(position).getCustomAttributes());
                campaignBuilder.getListener().onItemClickListener(data);
            }
        } catch (Exception e) {

        }


    }

    @Override
    public void onLoadMore() {

    }

    private void getSavedData() {
        PromotionResponse response = CommonData.getPromotionResponse();
        arrayList = new ArrayList<>();
        if(response != null && response.getData() != null && response.getData().size() > 0) {
            arrayList.addAll(response.getData());
            if(campaignAdapter != null) {
                campaignAdapter.setData(arrayList);
            }
        }
        if(arrayList.size() < 1) {
            refreshLayout.setRefreshing(true);
        }
        fetchData();
    }

    private void fetchData() {
        if(isNetworkAvailable()) {
            if(startOffset != 0) {
                isPagingApiInProgress= true;
                campaignAdapter.showPaginationProgressBar(true, true);
            }
            new ApiGetMobileNotification(getActivity(), new NotificationListener() {
                @Override
                public void onSucessListener(PromotionResponse response) {
                    try {

                        if (arrayList == null)
                            arrayList = new ArrayList<>();

                        if (startOffset == 0) {
                            arrayList.clear();
                            CommonData.savePromotionResponse(response);
                        }

                        if (startOffset != 0) {
                            isPagingApiInProgress = false;
                            campaignAdapter.showPaginationProgressBar(false, true);
                        }

                        HashSet<String> unreadDate = CommonData.getAnnouncementCount();
                        if(unreadDate.size() > 0) {
                            HashSet<String> list = new HashSet();
                            for(Data data : response.getData())
                                list.add(String.valueOf(data.getChannelId()));
                            if(list.size() > 0) {
                                unreadDate.removeAll(list);
                                if (BumbleConfig.getInstance().getCallbackListener() != null) {
                                    BumbleConfig.getInstance().getCallbackListener().unreadAnnouncementsCount(unreadDate.size());
                                }
                                CommonData.setAnnouncementCount(unreadDate);
                            }
                        }

                        arrayList.addAll(response.getData());

                        if (arrayList.size() == 0) {
                            llNoNotifications.setVisibility(View.VISIBLE);
                            delete.setVisibility(View.GONE);
                        } else {
                            llNoNotifications.setVisibility(View.GONE);
                            delete.setVisibility(View.VISIBLE);
                        }

                        if(campaignAdapter != null) {
                            campaignAdapter.setData(arrayList);
                        }
                        refreshLayout.setRefreshing(false);
                        if(response.getData().size() == 0) {
                            hasMorePages = false;
                        } else {
                            hasMorePages = true;
                        }
                        //hasMorePages = response.getData().size() == offset;

                        Log.e("SIZE", "TOTAL SIZE = "+arrayList.size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailureListener() {
                    refreshLayout.setRefreshing(false);
                    if (startOffset != 0) {
                        isPagingApiInProgress = false;
                        campaignAdapter.showPaginationProgressBar(false, true);
                    }
                    if (arrayList.size() == 0) {
                        llNoNotifications.setVisibility(View.VISIBLE);

                    } else {
                        llNoNotifications.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onItemClickListener(String url) {

                }
            }).getNotificationData(startOffset, offset);
        } else {
            String text = Restring.getString(getActivity(), R.string.fugu_not_connected_to_internet);
            ToastUtil.getInstance(getActivity()).showToast(text);
            refreshLayout.setRefreshing(false);
            if (startOffset != 0) {
                isPagingApiInProgress = false;
                campaignAdapter.showPaginationProgressBar(false, true);
            }
        }
    }

    private void deleteAll() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to clear all notifications?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearData(-1, -1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    public void clearData(final long channelId, int position) {
        if(isNetworkAvailable()) {
            new ApiGetMobileNotification(getActivity(), new OnClearNotificationListener() {
                @Override
                public void onSucessListener(int position) {
                    if(position>-1) {
                        campaignAdapter.notifyItemRemoved(position);
                        arrayList.remove(position);
                        String text = Restring.getString(getActivity(), R.string.hippo_notifications_deleted);
                        ToastUtil.getInstance(getActivity()).showToast(text);
                    } else {
                        arrayList.clear();
                        campaignAdapter.notifyDataSetChanged();
                    }
                    if(arrayList.size() == 0) {
                        delete.setVisibility(View.GONE);
                        llNoNotifications.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure() {

                }
            }).clearNotification(channelId, position);
        } else {
            String text = Restring.getString(getActivity(), R.string.fugu_not_connected_to_internet);
            ToastUtil.getInstance(getActivity()).showToast(text);
        }
    }
}
