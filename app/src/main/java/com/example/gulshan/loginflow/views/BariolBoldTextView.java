package com.example.gulshan.loginflow.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import utils.MyUtilities;


public class BariolBoldTextView extends TextView {

	public BariolBoldTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public BariolBoldTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BariolBoldTextView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context pContext) {
		if (isInEditMode()) {
			return;
		}
		this.setTypeface(MyUtilities.getTypeface(pContext, "bariol_bold_webfont"));
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

			canvas.drawLine(mRect.left, baseline + 10, mRect.right+2, baseline + 10, mPaint);
		}

		super.onDraw(canvas);
	}
}
