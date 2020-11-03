package com.bumble.Utils.filepicker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumble.Utils.filepicker.Util;
import com.bumble.Utils.filepicker.filter.entity.NormalFile;
import com.bumblesdk.R;

import java.util.ArrayList;



/**
 * Created by Vincent Woo
 * Date: 2016/10/26
 * Time: 10:23
 */

public class NormalFilePickAdapter extends BaseAdapter<NormalFile, NormalFilePickAdapter.NormalFilePickViewHolder> {

    protected Context mContext;

    public NormalFilePickAdapter(Context ctx, int max) {
        this(ctx, new ArrayList<NormalFile>(), max);
    }

    public NormalFilePickAdapter(Context ctx, ArrayList<NormalFile> list, int max) {
        super(ctx, list);
        this.mContext = ctx;
    }

    @Override
    public NormalFilePickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.vw_layout_item_normal_file_pick, parent, false);
        return new NormalFilePickViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NormalFilePickViewHolder holder, final int position) {
        holder.mCbx.setVisibility(View.GONE);
        if(position == 0) {
            holder.mTvTitle.setText(R.string.hippo_browse_other_doc);
            holder.mIvIcon.setImageResource(R.drawable.hippo_ic_storage);
            holder.mTvTitle.setLines(1);
        } else {
            final NormalFile file = mList.get(position - 1);
            //String size = Formatter.formatShortFileSize(mContext, file.getSize());
            holder.mTvTitle.setText(Util.extractFileNameWithSuffix(file.getPath()));
            holder.mTvTitle.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            if (holder.mTvTitle.getMeasuredWidth() >
                    Util.getScreenWidth(mContext) - Util.dip2px(mContext, 10 + 32 + 10 + 48 + 10 * 2)) {
                holder.mTvTitle.setLines(2);
            } else {
                holder.mTvTitle.setLines(1);
            }

            if (file.getPath().toLowerCase().endsWith("xls") || file.getPath().toLowerCase().endsWith("xlsx")) {
                holder.mIvIcon.setImageResource(R.drawable.vw_ic_excel);
            } else if (file.getPath().toLowerCase().endsWith("doc") || file.getPath().toLowerCase().endsWith("docx")){
                holder.mIvIcon.setImageResource(R.drawable.vw_ic_word);
            } else if (file.getPath().toLowerCase().endsWith("ppt") || file.getPath().endsWith("pptx")){
                holder.mIvIcon.setImageResource(R.drawable.vw_ic_ppt);
            } else if (file.getPath().toLowerCase().endsWith("pdf")){
                holder.mIvIcon.setImageResource(R.drawable.vw_ic_pdf);
            } else if (file.getPath().toLowerCase().endsWith("txt")){
                holder.mIvIcon.setImageResource(R.drawable.vw_ic_txt);
            } else {
                holder.mIvIcon.setImageResource(R.drawable.vw_ic_file);
            }
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                if(index == 0) {
                    if (mListener != null) {
                        mListener.OnSelectStateChanged(true, null);
                    }
                } else {
                    mList.get(holder.getAdapterPosition()).setSelected(holder.mCbx.isSelected());
                    if (mListener != null) {
                        mListener.OnSelectStateChanged(holder.mCbx.isSelected(), mList.get(holder.getAdapterPosition()-1));
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class NormalFilePickViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvTitle;
        private ImageView mCbx;

        public NormalFilePickViewHolder(View itemView) {
            super(itemView);
            mIvIcon = itemView.findViewById(R.id.ic_file);
            mTvTitle = itemView.findViewById(R.id.tv_file_title);
            mCbx = itemView.findViewById(R.id.cbx);
        }
    }

    public Bitmap writeOnDrawable(int drawableId, String text){

        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);

        Canvas canvas = new Canvas(bm);
        canvas.drawText(text, 0, bm.getHeight()/2, paint);

        return bm;
    }
}
