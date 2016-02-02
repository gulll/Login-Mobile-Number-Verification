package com.example.gulshan.loginflow.views;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;


public class BariolTextView extends TextView {

    private boolean mDontConnsumeNonUrlClicks;
    private boolean mLinkHit;

    public BariolTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public BariolTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BariolTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context pContext) {
        if (isInEditMode()) {
            return;
        }
//        this.setTypeface(MyUtilities.getTypeface(pContext, "bariol"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mLinkHit = false;
        boolean result = super.onTouchEvent(event);

        if (mDontConnsumeNonUrlClicks) {
            return mLinkHit;
        }
        return result;

    }

    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        private static LocalLinkMovementMethod mInstance;


        public static LocalLinkMovementMethod getInstance() {
            if (mInstance == null) {
                mInstance = new LocalLinkMovementMethod();
            }

            return mInstance;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(
                        off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                    }
                    if (widget instanceof BariolTextView) {
                        ((BariolTextView) widget).mLinkHit = true;
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }

    public void setmDontConnsumeNonUrlClicks(boolean mDontConnsumeNonUrlClicks) {
        this.mDontConnsumeNonUrlClicks = mDontConnsumeNonUrlClicks;
    }
}
