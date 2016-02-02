package com.example.gulshan.loginflow.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import utils.MyUtilities;


public class BariolTextViewPressable extends TextView {

	public BariolTextViewPressable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public BariolTextViewPressable(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BariolTextViewPressable(Context context) {
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
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setAlpha(0.8f);
			break;
			
		case MotionEvent.ACTION_UP:
			setAlpha(1.0f);
			break;
		case MotionEvent.ACTION_CANCEL:
			setAlpha(1.0f);
			break;

		default:
			break;
		}
		
		return super.onTouchEvent(event);
	}
}
