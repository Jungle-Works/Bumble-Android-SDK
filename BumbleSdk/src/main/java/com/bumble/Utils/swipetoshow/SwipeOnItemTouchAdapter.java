package com.bumble.Utils.swipetoshow;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jinks on 15/7/2.
 */
public abstract class SwipeOnItemTouchAdapter implements RecyclerView.OnItemTouchListener {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private final int TOUCH_SLOP;

    public SwipeOnItemTouchAdapter(Context context, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        TOUCH_SLOP = ViewConfiguration.get(context).getScaledTouchSlop();
        this.context = context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
    }

    int startX;
    int startY;
    boolean beginSlide;
    boolean beginScroll;
    boolean animatingPollute;

    SwipeHolder targetView;
    SwipeHolder animatingView;

    int targetPosition;

    boolean pendingHiddenClick;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (enableSwipe)
            return false;
        if (animatingView != null && animatingView.isAnimating()) {
            animatingPollute = true;
            return true;
        } else {
            animatingView = null;
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) motionEvent.getX();
                startY = (int) motionEvent.getY();
                beginSlide = false;
                beginScroll = false;
                animatingPollute = false;
                if (targetView != null) {
                    float tx, ty;
                    tx = startX;
                    ty = startY;
                    if (isXInHiddenView(tx, ty)) {
                        pendingHiddenClick = true;
                    } else {
                        targetView.animateCollapse();
                        animatingView = targetView;
                        targetView = null;
                        targetPosition = -1;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (animatingPollute)
                    return false;
                if (beginScroll) {
                    return false;
                }
                if (beginSlide) {
                    return true;
                }
                if (!beginSlide) {
                    int horizontalDelta = (int) (motionEvent.getX() - startX);
                    int verticalDelta = (int) (motionEvent.getY() - startY);
                    if (Math.abs(horizontalDelta) > TOUCH_SLOP) {
                        if (!(recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY()) instanceof SwipeHolder)) {
                            return false;
                        }
                        beginSlide = true;
                        targetView = (SwipeHolder) recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                        if (targetView == null) {
                            beginSlide = false;
                            return false;
                        }
                        targetPosition = layoutManager.getPosition(targetView);
                        startX = (int) motionEvent.getX();
                        return true;
                    } else if (Math.abs(verticalDelta) > TOUCH_SLOP) {
                        beginScroll = true;
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (animatingPollute) {
                    animatingPollute = false;
                    return false;
                }
                if (targetView != null) {
                    int dx = (int) motionEvent.getX();
                    int dy = (int) motionEvent.getY();
                    if (isXInHiddenView(dx, dy)) {
                        onItemHiddenClick(targetView, targetPosition);
                        targetView = null;
                    }
                    break;
                }
                if (!beginSlide && !beginScroll && targetView == null) {
                    View clickView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (clickView != null) {
                        int clickPosition = layoutManager.getPosition(clickView);
                        onItemClick(clickPosition);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                beginSlide = false;
                beginScroll = false;
                animatingPollute = false;

        }
        return false;
    }


    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (animatingView != null && animatingView.isAnimating()) {
            animatingPollute = true;
            return;
        } else {
            animatingView = null;
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (animatingPollute) {
                    return;
                }
                targetView.slide((int) (startX - motionEvent.getX()));
                break;
            case MotionEvent.ACTION_UP:
                if (animatingPollute) {
                    animatingPollute = false;
                    return;
                }
                boolean isShow = targetView.determineShowOrHide();
                if (!isShow) {
                    targetView = null;
                    targetPosition = -1;
                }
                break;
        }
    }

    public boolean isBusy() {
        return targetView != null;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isXInHiddenView(float x, float y) {
        return recyclerView.findChildViewUnder(x, y) == targetView && targetView.isXInHideArea((int) x);
    }

    public abstract void onItemHiddenClick(SwipeHolder swipeHolder, int position);

    public abstract void onItemClick(int position);


    private boolean enableSwipe;

    /**
     * to enable swipe,set this to true
     *
     * @param enableSwipe
     */
    public void setEnableSwipe(boolean enableSwipe) {
        this.enableSwipe = enableSwipe;
    }
}