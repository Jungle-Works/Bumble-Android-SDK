package com.bumble.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.bumblesdk.R;

public class RatingBarMenuFeedback extends LinearLayout {


    private static final int LOW_RATING_RED = Color.parseColor("#FB9758");
    private static final int MEDIUM_RATING_YELLOW = Color.parseColor("#FFD365");
    private static final int GOOD_RATING_GREEN = Color.parseColor("#8DCF61");
    private static final int RATING_DISABLED_COLOR = Color.parseColor("#efefef");
    private static final int NO_RATING_COLOR =Color.parseColor("#595968");
    private boolean mAnimate=true;
    private boolean mDisplayText=true;

    public IRatingBarCallbacks getOnScoreChanged() {
        return onScoreChanged;
    }

    public void setOnScoreChanged(IRatingBarCallbacks onScoreChanged) {
        this.onScoreChanged = onScoreChanged;
    }

    public interface IRatingBarCallbacks {
        void scoreChanged(float score);
    }


    private int mMaxStars = 5;
    private float mCurrentScore = 0f;
    private int mStarOnResource ;
    private int mStarOffResource;
    private int mStarHalfResource = R.drawable.ic_menu_feedback_star_off;
    private int belowTextColor;
    private boolean singleColor;
    private int starColor, noStarColor;
    private TextView[] mStarsViews;
    private float mStarPadding, viewMargin;
    private float mTextSize, compoundDrawablePadding, viewMinWidth;
    private IRatingBarCallbacks onScoreChanged;
    private int mLastStarId;
    private boolean mOnlyForDisplay;
    private double mLastX;
    private boolean mHalfStars = true;

    public RatingBarMenuFeedback(Context context) {
        super(context);

        init();
    }


    public TextView[] getmStarsViews() {
        return mStarsViews;
    }

    public float getScore() {
        return mCurrentScore;
    }

    public void setScore(float score,boolean giveCallback) {
        score = Math.round(score * 2) / 2.0f;
        if (!mHalfStars)
            score = Math.round(score);
        mCurrentScore = score;
        refreshStars();
        if (giveCallback) {
            if (onScoreChanged != null)
                onScoreChanged.scoreChanged(mCurrentScore);
        }
    }


    public void setStarResource(int fullIcon, int normalIcon) {
        this.mStarOnResource = fullIcon;
        this.mStarOffResource = normalIcon;
        this.mStarHalfResource = normalIcon;

    }

    public void setScrollToSelect(boolean enabled) {
        mOnlyForDisplay = !enabled;
    }

    public RatingBarMenuFeedback(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(attrs, context);
        init();
    }

    private void initializeAttributes(AttributeSet attrs, Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRatingBar);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CustomRatingBar_maxStars)
                mMaxStars = a.getInt(attr, 5);
            else if (attr == R.styleable.CustomRatingBar_belowTextColor)
                belowTextColor = a.getColor(attr, ContextCompat.getColor(context, R.color.fugu_text_color_primary));
            else if (attr == R.styleable.CustomRatingBar_stars)
                mCurrentScore = a.getFloat(attr, 2.5f);
            else if (attr == R.styleable.CustomRatingBar_starHalf)
                mStarHalfResource = a.getResourceId(attr, android.R.drawable.star_on);
            else if (attr == R.styleable.CustomRatingBar_starOn)
                mStarOnResource = a.getResourceId(attr, android.R.drawable.star_on);
            else if (attr == R.styleable.CustomRatingBar_starOff)
                mStarOffResource = a.getResourceId(attr, android.R.drawable.star_off);
            else if (attr == R.styleable.CustomRatingBar_starPadding)
                mStarPadding = a.getDimension(attr, 0);
            else if (attr == R.styleable.CustomRatingBar_belowTextSize)
                mTextSize = a.getDimension(attr, 0);
            else if (attr == R.styleable.CustomRatingBar_viewMargin)
                viewMargin = a.getDimension(attr, 0);
            else if (attr == R.styleable.CustomRatingBar_viewMinWidth)
                viewMinWidth = a.getDimension(attr, 0);
            else if (attr == R.styleable.CustomRatingBar_compoundDrawablePadding)
                compoundDrawablePadding = a.getDimension(attr, 0);
            else if (attr == R.styleable.CustomRatingBar_onlyForDisplay)
                mOnlyForDisplay = a.getBoolean(attr, false);
            else if (attr == R.styleable.CustomRatingBar_halfStars)
                mHalfStars = a.getBoolean(attr, true);
            else if (attr == R.styleable.CustomRatingBar_animate)
                mAnimate = a.getBoolean(attr, true);
            else if (attr == R.styleable.CustomRatingBar_displayText)
                mDisplayText = a.getBoolean(attr, true);
            else if (attr == R.styleable.CustomRatingBar_singleColor)
                singleColor = a.getBoolean(attr, false);
            else if (attr == R.styleable.CustomRatingBar_starColor)
                starColor = a.getColor(attr, ContextCompat.getColor(context, R.color.fugu_theme_color_primary));
            else if (attr == R.styleable.CustomRatingBar_noStarColor)
                noStarColor = a.getColor(attr, ContextCompat.getColor(context, R.color.fugu_secondary_text_msg_you));
        }
        a.recycle();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RatingBarMenuFeedback(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeAttributes(attrs, context);
        init();
    }

    void init() {
        mStarsViews = new TextView[mMaxStars];
        for (int i = 0; i < mMaxStars; i++) {
            TextView v = createStar();
            addView(v);
            mStarsViews[i] = v;
        }
        refreshStars();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * hardcore math over here
     *
     * @param x
     * @return
     */
    private float getScoreForPosition(float x) {
        if (mHalfStars)
            return (float) Math.round(((x / ((float) getWidth() / (mMaxStars * 3f))) / 3f) * 2f) / 2;
       /* float value = (float) Math.round((x / ((float) getWidth() / (mMaxStars))));
        value = value <= 0 ? 1 : value;
        value = value > mMaxStars ? mMaxStars : value;*/
        Float dividend =   (x / ((float) getWidth() / (mMaxStars)));
        int finalValue = dividend.intValue()+1;
        return finalValue>mMaxStars?mMaxStars:finalValue<0?0:finalValue;

    }

    private int getImageForScore(float score) {
        if (score > 0)
            return Math.round(score) - 1;
        else return -1;
    }

    private void refreshStars() {
        boolean flagHalf = (mCurrentScore != 0 && (mCurrentScore % 0.5 == 0)) && mHalfStars;
        for (int i = 1; i <= mMaxStars; i++) {

            if (i <= mCurrentScore) {
                mStarsViews[i - 1].setCompoundDrawablesWithIntrinsicBounds(0, mStarOnResource, 0, 0);


                if(mCurrentScore>=3){
                    starColor = GOOD_RATING_GREEN;
                }else if(mCurrentScore>1){
                    starColor = MEDIUM_RATING_YELLOW;
                }else {
                    starColor = LOW_RATING_RED;
                }

                mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(starColor, PorterDuff.Mode.SRC_ATOP);

                /*if(singleColor){
                    mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(starColor, PorterDuff.Mode.SRC_ATOP);
                } else {
                    switch (i) {
                        case 1:
                            mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(LOW_RATING_RED, PorterDuff.Mode.SRC_ATOP);
                            break;
                        case 2:
                            mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(MEDIUM_RATING_YELLOW, PorterDuff.Mode.SRC_ATOP);
                            break;
                        default:
                            mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(GOOD_RATING_GREEN, PorterDuff.Mode.SRC_ATOP);
                            break;
                    }
                }*/


            } else {
                if (flagHalf && i - 0.5 <= mCurrentScore)
                    mStarsViews[i - 1].setCompoundDrawablesWithIntrinsicBounds(0, mStarHalfResource, 0, 0);
                else
                    mStarsViews[i - 1].setCompoundDrawablesWithIntrinsicBounds(0, mStarOffResource, 0, 0);

                if(singleColor){
                    mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(RATING_DISABLED_COLOR, PorterDuff.Mode.SRC_ATOP);
                } else {
                    mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(RATING_DISABLED_COLOR, PorterDuff.Mode.SRC_ATOP);
                }
            }

            if (mDisplayText) {
                if (i == mCurrentScore) {
                    switch (i) {
                        case 1:
                            mStarsViews[i - 1].setText(getContext().getString(R.string.hippo_smile_rating_terrible));
                            break;
                        case 2:
                            mStarsViews[i - 1].setText(getContext().getString(R.string.hippo_smile_rating_bad));
                            break;
                        case 3:
                            mStarsViews[i - 1].setText(getContext().getString(R.string.hippo_smile_rating_okay));
                            break;
                        case 4:
                            mStarsViews[i - 1].setText(getContext().getString(R.string.hippo_smile_rating_good));
                            break;
                        case 5:
                            mStarsViews[i - 1].setText(getContext().getString(R.string.hippo_smile_rating_great));
                            break;
                    }
                } else {
                    mStarsViews[i - 1].setText(null);
                }
            }

        }
    }

    private TextView createStar() {
        TextView v = new TextView(getContext());
        v.setMinWidth((int) (viewMinWidth > 0 ? viewMinWidth : (100.0f)));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.leftMargin = (int) (viewMargin > 0 ? viewMargin : (5.0f));
        params.rightMargin = (int) (viewMargin > 0 ? viewMargin : (5.0f));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.setMarginStart((int) (viewMargin > 0 ? viewMargin : (5.0f)));
            params.setMarginEnd((int) (viewMargin > 0 ? viewMargin : (5.0f)));
        }

        params.bottomMargin = (int) (viewMargin > 0 ? viewMargin : (5.0f));
        params.topMargin = (int) (viewMargin > 0 ? viewMargin : (5.0f));
        v.setGravity(Gravity.CENTER);
        v.setCompoundDrawablePadding((int) (compoundDrawablePadding > 0 ? compoundDrawablePadding : ( 5.0f)));
        v.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize > 0 ? mTextSize : 14.0f);
        //v.setTypeface(Fonts.mavenMedium(getContext()), Typeface.BOLD);
        v.setTextColor(belowTextColor > 0 ? belowTextColor : ContextCompat.getColor(getContext(), R.color.fugu_text_color_primary));
        v.setLayoutParams(params);
        v.setCompoundDrawablesWithIntrinsicBounds(0, mStarOffResource, 0, 0);
        v.setPaddingRelative((int)mStarPadding, (int)mStarPadding, (int)mStarPadding, (int)mStarPadding);
        return v;
    }

    private TextView getImageView(int position) {
        try {
            return mStarsViews[position];
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (mOnlyForDisplay)
            return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                animateStarRelease(getImageView(mLastStarId), mCurrentScore);
                mLastStarId = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getX() - mLastX) > 80)
                    requestDisallowInterceptTouchEvent(true);
                float lastscore = mCurrentScore;
                mCurrentScore = getScoreForPosition(event.getX());
                if (lastscore != mCurrentScore) {
                    animateStarRelease(getImageView(mLastStarId), mCurrentScore);
                    animateStarPressed(getImageView(getImageForScore(mCurrentScore)), mCurrentScore);
                    mLastStarId = getImageForScore(mCurrentScore);
                    refreshStars();
                    if (onScoreChanged != null)
                        onScoreChanged.scoreChanged(mCurrentScore);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                lastscore = mCurrentScore;
                mCurrentScore = getScoreForPosition(event.getX());
                animateStarPressed(getImageView(getImageForScore(mCurrentScore)), mCurrentScore);
                mLastStarId = getImageForScore(mCurrentScore);
                if (lastscore != mCurrentScore) {
                    refreshStars();
                    if (onScoreChanged != null)
                        onScoreChanged.scoreChanged(mCurrentScore);
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                animateStarRelease(getImageView(getImageForScore(mCurrentScore)), mCurrentScore);
                break;
        }
        return true;
    }

    private void animateStarPressed(TextView star, float score) {
        if (mAnimate) {
            if (star != null)
                ViewCompat.animate(star).scaleX(1.2f).scaleY(1.2f).setDuration(100).start();
        }
//            star.setTextSize(TypedValue.COMPLEX_UNIT_PX, 28);

    }

    private void animateStarRelease(TextView star, float score) {
        if (mAnimate) {
            if (star != null) {
                ViewCompat.animate(star).scaleX(1f).scaleY(1f).setDuration(100).start();
                //            star.setTextSize(TypedValue.COMPLEX_UNIT_PX, 26);

            }
        }
    }

    public boolean isHalfStars() {
        return mHalfStars;
    }

    public void setHalfStars(boolean halfStars) {
        mHalfStars = halfStars;
    }
    public void setEnabled(boolean isEnabled){
        mOnlyForDisplay = !isEnabled;
    }

    public void setRatingDisabled(boolean disabled){
        if (disabled) {
            for (int i = 1; i <= mMaxStars; i++) {
                mStarsViews[i - 1].getCompoundDrawables()[1].mutate().setColorFilter(RATING_DISABLED_COLOR, PorterDuff.Mode.SRC_ATOP);
            }
        }
        else{
            refreshStars();
        }
    }
}