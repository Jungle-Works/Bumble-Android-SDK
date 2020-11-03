package com.bumble.adapter;

import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumble.BumbleConfig;
import com.bumble.HippoColorConfig;
import com.bumble.Utils.DateUtils;
import com.bumble.Utils.ProgressBarDisplayRunnable;
import com.bumble.Utils.Restring;
import com.bumble.Utils.RoundedCornersTransformation;
import com.bumble.Utils.Utils;
import com.bumble.activity.ImageDisplayActivityNew;
import com.bumble.database.CommonData;
import com.bumble.interfaces.OnItemClickListener;
import com.bumble.model.Image;
import com.bumble.model.promotional.Data;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import java.util.ArrayList;

import com.bumblesdk.R;

public class CampaignAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OnItemClickListener itemClickListener;
    private HippoColorConfig hippoColorConfig;
    private ArrayList<Data> arrayList;
    private RecyclerView recyclerView;
    private static final int VIEW_TYPE = 1;
    private static final int VIEW_TYPE_LOADING = 2;


    private String outputFormat = "dd MMM, yy h:mm a";
    private Typeface customBold, customNormal;

    public CampaignAdapter(Activity activity, ArrayList<Data> arrayList, RecyclerView recyclerView, OnItemClickListener itemClickListener) {
        this.arrayList = arrayList;
        this.recyclerView = recyclerView;
        this.itemClickListener = itemClickListener;
        hippoColorConfig = CommonData.getColorConfig();
        if (BumbleConfig.getInstance().getMobileCampaignBuilder() != null &&
                !TextUtils.isEmpty(BumbleConfig.getInstance().getMobileCampaignBuilder().getParseFormat()))
            outputFormat = BumbleConfig.getInstance().getMobileCampaignBuilder().getParseFormat();
        customBold = Typeface.createFromAsset(activity.getAssets(),  "fonts/ProximaNova-Sbold.ttf");
        customNormal = Typeface.createFromAsset(activity.getAssets(),  "fonts/ProximaNova-Reg.ttf");

    }

    public void setData(ArrayList<Data> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        if (viewType == VIEW_TYPE_LOADING) {
            return new LoadMoreHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.hippo_layout_loaddata, viewGroup, false));
        } else {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.hippo_item_campaign, viewGroup, false));
        }
    }

    private static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        if (holder instanceof CampaignAdapter.ViewHolder) {
            final CampaignAdapter.ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.title.setText(arrayList.get(pos).getTitle());

            String description = arrayList.get(pos).getDescription();
            int count = countLines(description);

            viewHolder.description.setText(description);
            viewHolder.tvMsg.setText(description);

            viewHolder.clearBtn.setText(Restring.getString(mContext, R.string.hippo_clear));

            if(arrayList.get(pos).getShowMore() == 2) {
                String text = Restring.getString(mContext, R.string.hippo_read_less);
                viewHolder.readStatus.setText(text);
                viewHolder.tvMsg.setVisibility(View.GONE);
                viewHolder.description.setVisibility(View.VISIBLE);
                viewHolder.readStatus.setVisibility(View.VISIBLE);
            } else {
                if(count>0 && description.length() <160) {
                    viewHolder.description.setVisibility(View.VISIBLE);
                    viewHolder.tvMsg.setVisibility(View.GONE);
                    viewHolder.readStatus.setVisibility(View.GONE);
                } else {
                    String text = Restring.getString(mContext, R.string.hippo_read_more);
                    viewHolder.readStatus.setText(text);
                    viewHolder.readStatus.setVisibility(View.VISIBLE);
                    viewHolder.tvMsg.setVisibility(View.VISIBLE);
                    viewHolder.description.setVisibility(View.GONE);
                }
            }


            String time = DateUtils.getRelativeDate(DateUtils.getInstance().convertToLocal(arrayList.get(pos).getCreatedAt()), true);
            viewHolder.dateTime.setText(time);

            String imagePath = "";

            try {
                imagePath = arrayList.get(pos).getCustomAttributes().getImage().getImageUrl();
            } catch (Exception e) {

            }

            if (TextUtils.isEmpty(imagePath)) {
                viewHolder.imageView.setVisibility(View.GONE);
            } else {
                float dpCalculation = mContext.getResources().getDisplayMetrics().density;

                ViewTreeObserver vto = viewHolder.mainLayout.getViewTreeObserver();
                vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        viewHolder.mainLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        int finalWidth = viewHolder.mainLayout.getMeasuredWidth();
                        viewHolder.imageView.getLayoutParams().height = (int) (finalWidth/2.5);
                        viewHolder.imageView.setVisibility(View.VISIBLE);
                        return true;
                    }
                });

                RequestOptions myOptions = RequestOptions
                        .bitmapTransform(new RoundedCornersTransformation(mContext, 10, 1))
                        .placeholder(ContextCompat.getDrawable(mContext, R.drawable.hippo_placeholder))
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(ContextCompat.getDrawable(mContext, R.drawable.hippo_placeholder));

                Glide.with(mContext).load(imagePath)
                        .fitCenter()
                        .apply(myOptions)
                        .transform(new CenterCrop(),new RoundedCorners(12))
                        .into(viewHolder.imageView);

            }

            viewHolder.title.setTypeface(customBold);
            viewHolder.description.setTypeface(customNormal);
            viewHolder.tvMsg.setTypeface(customNormal);
            viewHolder.dateTime.setTypeface(customNormal);
            viewHolder.readStatus.setTypeface(customNormal);
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList == null) ? 0 : arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (arrayList.get(position).getUserId() == -3) {
                return VIEW_TYPE_LOADING;
            } else {
                return VIEW_TYPE;
            }
        } catch (Exception e) {
            return VIEW_TYPE;
        }
    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        public LoadMoreHolder(View itemView) {
            super(itemView);

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mainLayout;
        private ImageView imageView;
        private TextView title;
        private TextView description;
        private TextView dateTime;
        private TextView tvMsg, readStatus;
        private Button clearBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            imageView = itemView.findViewById(R.id.ivImage);
            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);

            tvMsg = itemView.findViewById(R.id.tvMsg);
            readStatus = itemView.findViewById(R.id.readStatus);

            dateTime = itemView.findViewById(R.id.tvDateTime);
            clearBtn = itemView.findViewById(R.id.clearBtn);

            setTextSize(title, 16);
            setTextSize(description, 14);
            setTextSize(readStatus, 14);
            setTextSize(dateTime, 13.4f);


            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null) {
                        itemClickListener.onClickListener(getAdapterPosition());
                    }
                }
            });

            readStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(description.getVisibility() == View.VISIBLE) {
                        arrayList.get(getAdapterPosition()).setShowMore(1);
                    } else {
                        arrayList.get(getAdapterPosition()).setShowMore(2);
                    }
                    notifyItemChanged(getAdapterPosition());
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String imagePath = "";

                    try {
                        imagePath = arrayList.get(getAdapterPosition()).getCustomAttributes().getImage().getImageUrl();
                        showImageDialog((Activity) mContext, imagePath, imageView);
                    } catch (Exception e) {

                    }
                }
            });

        }
    }

    public void showPaginationProgressBar(boolean show, boolean notifyAdapter) {
        if(arrayList == null || arrayList.size() == 0)
            return;

        boolean isProgressBarExist = arrayList.get(arrayList.size() - 1).getUserId() == -3;
        if(show) {
            if (!isProgressBarExist) {
                arrayList.add(CampaignAdapter.ProgressBarModel.getInstance());
                if(notifyAdapter){
                    recyclerView.post(getProgressDisplayRunnable(true, arrayList.size()));
                }
            }
        } else {
            if(arrayList.remove(CampaignAdapter.ProgressBarModel.getInstance())){
                if(notifyAdapter){
                    recyclerView.post(getProgressDisplayRunnable(false, arrayList.size()));
                }
            }
        }
    }
    private ProgressBarDisplayRunnable progressBarDisplayRunnable;
    private ProgressBarDisplayRunnable getProgressDisplayRunnable(boolean isInsert, int position){
        if(progressBarDisplayRunnable == null){
            progressBarDisplayRunnable = new ProgressBarDisplayRunnable(this);
        }
        progressBarDisplayRunnable.setInsert(isInsert);
        progressBarDisplayRunnable.setPosition(position);

        return progressBarDisplayRunnable;
    }


    private static class ProgressBarModel {
        private static Data progressBarModel;
        private ProgressBarModel() {
        }
        public static Data getInstance(){
            if(progressBarModel ==null)
                progressBarModel = new Data(-3);
            return progressBarModel;
        }
    }

    public void setTextSize(TextView textView, float size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    private void showImageDialog(Activity activity, String imgUrl, ImageView imageView) {
        try {
            if (!Utils.preventMultipleClicks()) {
                return;
            }

            Intent profileImageIntent = new Intent(activity, ImageDisplayActivityNew.class);
            Image profileImage = new Image(imgUrl, imgUrl, "imageOne", "", "");
            profileImageIntent.putExtra("image", profileImage);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                    imageView, "imageOne");
            activity.startActivity(profileImageIntent, options.toBundle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}