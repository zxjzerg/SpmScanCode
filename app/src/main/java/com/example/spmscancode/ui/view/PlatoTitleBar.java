package com.example.spmscancode.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spmscancode.R;

/**
 * 标题栏
 * Created by Andrew on 14-10-13.
 */
public class PlatoTitleBar extends RelativeLayout {

    private int mLeftBtnIcon;
    private int mRightBtnIcon;
    private String mLeftBtnText;
    private String mRightBtnText;
    private View mBtnLeft, mBtnRight;
    private TextView mTvTitle;
    private String mTitle;
    private LayoutParams mLeftBtnParams;
    private LayoutParams mRightBtnParams;

    public PlatoTitleBar(Context context) {
        super(context);
    }

    public PlatoTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PlatoTitleBar, 0, 0);

        try {
            mLeftBtnIcon = a.getResourceId(R.styleable.PlatoTitleBar_leftBtnIcon, 0);
            mRightBtnIcon = a.getResourceId(R.styleable.PlatoTitleBar_rightBtnIcon, 0);
            mLeftBtnText = a.getString(R.styleable.PlatoTitleBar_leftBtnText);
            mRightBtnText = a.getString(R.styleable.PlatoTitleBar_rightBtnText);
            mTitle = a.getString(R.styleable.PlatoTitleBar_titleText);
        } finally {
            a.recycle();
        }

        init();
    }

    public PlatoTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        mLeftBtnParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLeftBtnParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mLeftBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mRightBtnParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mRightBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (mLeftBtnIcon != 0) {
            addLeftButton(mLeftBtnIcon);
        } else if (!TextUtils.isEmpty(mLeftBtnText)) {
            addLeftButton(mLeftBtnText);
        }

        if (mRightBtnIcon != 0) {
            addRightButton(mRightBtnIcon);
        } else if (!TextUtils.isEmpty(mRightBtnText)) {
            addRightButton(mRightBtnText);
        }

        mTvTitle = new TextView(getContext());
        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        if (!isInEditMode()) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.title_text_size));
            mTvTitle.setTextColor(getResources().getColor(android.R.color.white));
        }
        mTvTitle.setLayoutParams(titleParams);
        if (!TextUtils.isEmpty(mTitle)) {
            mTvTitle.setText(mTitle);
        }
        addView(mTvTitle);

    }

    public void setLeftBtnClickListener(OnClickListener leftBtnClickListener) {
        if (mBtnLeft != null && leftBtnClickListener != null) {
            mBtnLeft.setOnClickListener(leftBtnClickListener);
        }
    }

    public void setRightBtnClickListener(OnClickListener rightBtnClickListener) {
        if (mBtnRight != null && rightBtnClickListener != null) {
            mBtnRight.setOnClickListener(rightBtnClickListener);
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
        invalidate();
        requestLayout();
    }

    public void clearButtons() {
        clearLeftButton();
        clearRightButton();
    }

    public void clearLeftButton() {
        if (mBtnLeft != null) {
            removeView(mBtnLeft);
            invalidate();
            requestLayout();
        }
    }

    public void clearRightButton() {
        if (mBtnRight != null) {
            removeView(mBtnRight);
            invalidate();
            requestLayout();
            mBtnRight = null;
        }
    }

    public void addLeftButton(String text) {
        mBtnLeft = new Button(getContext());
        mBtnLeft.setPadding(20, 0, 0, 0);
        mBtnLeft.setLayoutParams(mLeftBtnParams);
        mBtnLeft.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ((Button) mBtnLeft).setText(text);
        ((Button) mBtnLeft).setTextSize(21);
        ((Button) mBtnLeft).setTextColor(getResources().getColorStateList(android.R.color.white));
        addView(mBtnLeft);
        invalidate();
        requestLayout();
    }

    public void addLeftButton(int iconId) {
        if (mBtnLeft == null) {
            mBtnLeft = new ImageView(getContext());
            mBtnLeft.setPadding(20, 0, 50, 0);
            mBtnLeft.setLayoutParams(mLeftBtnParams);
            addView(mBtnLeft);
        }
        ((ImageView) mBtnLeft).setImageResource(iconId);

        invalidate();
        requestLayout();
    }

    public void addRightButton(String text) {
        if (mBtnRight == null) {
            mBtnRight = new Button(getContext());
            mBtnRight.setPadding(0, 0, 20, 0);
            mBtnRight.setLayoutParams(mRightBtnParams);
            mBtnRight.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            ((Button) mBtnRight).setTextSize(15);
            ((Button) mBtnRight).setTextColor(getResources().getColorStateList(android.R.color.white));
            addView(mBtnRight);
        }
        ((Button) mBtnRight).setText(text);

        invalidate();
        requestLayout();
    }

    public void addRightButton(int iconId) {
        if (mBtnRight == null) {
            mBtnRight = new ImageView(getContext());
            mBtnRight.setPadding(50, 0, 50, 0);
            mBtnRight.setLayoutParams(mRightBtnParams);
            addView(mBtnRight);
        }
        ((ImageView) mBtnRight).setImageResource(iconId);

        invalidate();
        requestLayout();
    }

}
