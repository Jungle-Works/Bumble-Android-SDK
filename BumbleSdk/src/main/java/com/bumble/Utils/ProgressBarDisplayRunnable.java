package com.bumble.Utils;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by gurmail on 07/11/17.
 */

public class ProgressBarDisplayRunnable implements Runnable {
    private RecyclerView.Adapter penaltyAdapter;
    private int position;
    private boolean isInsert;

    public ProgressBarDisplayRunnable(RecyclerView.Adapter penaltyAdapter) {
        this.penaltyAdapter = penaltyAdapter;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setInsert(boolean insert) {
        isInsert = insert;
    }

    @Override
    public void run() {
        if (isInsert) {
            penaltyAdapter.notifyItemInserted(position);

        } else {
            //penaltyAdapter.notifyItemRemoved(position);

        }

    }
}