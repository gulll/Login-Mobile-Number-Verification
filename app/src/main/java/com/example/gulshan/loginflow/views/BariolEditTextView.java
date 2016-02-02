package com.example.gulshan.loginflow.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import utils.MyUtilities;

public class BariolEditTextView extends EditText {

    private Fragment mBaseFragment;
    private boolean shouldInterceptBackKey;
	private Activity mBaseActivity;

	public BariolEditTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public BariolEditTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BariolEditTextView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context pContext) {
		if (isInEditMode()) {
			return;
		}
		this.setTypeface(MyUtilities.getTypeface(pContext, "bariol"));
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		if (shouldInterceptBackKey && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (mBaseFragment != null) {
//			mBaseFragment.backKeyPressed();
			} else {
//				mBaseActivity.onKeyBoardDown();
			}
			return true;
		}
		return false;
	}

    public void setShouldInterceptBackKey(boolean shouldInterceptBackKey, Fragment pBaseFragment) {
        this.shouldInterceptBackKey = shouldInterceptBackKey;
        mBaseFragment = pBaseFragment;
    }

	public void setShouldInterceptBackKey(boolean shouldInterceptBackKey, Activity pBaseActivity) {
		this.shouldInterceptBackKey = shouldInterceptBackKey;
		mBaseActivity = pBaseActivity;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int count = getLineCount();
		Rect mRect = new Rect();
		Paint mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(0x8000a287);
		mPaint.setStrokeWidth(3);

		for (int i = 0; i < count; i++) {
			int baseline = getLineBounds(i, mRect);

			canvas.drawLine(mRect.left-2, baseline + 8, mRect.right+2, baseline + 8, mPaint);
		}

		super.onDraw(canvas);
	}
}
