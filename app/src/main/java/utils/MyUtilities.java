package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gulshan.loginflow.R;

//import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyUtilities {

    private static final String LOG_TAG = MyUtilities.class.getSimpleName();


    private static HashMap<String, Typeface> typefaces = new HashMap<String, Typeface>();

    /**
     * @param ctx
     * @param typefaceName
     * @return
     */
    public static Typeface getTypeface(Context ctx, String typefaceName) {
        if (!typefaces.containsKey(typefaceName)) {
            Typeface tempTypeface = Typeface.createFromAsset(ctx.getAssets(),
                    typefaceName + ".ttf");
            typefaces.put(typefaceName, tempTypeface);
        }
        return typefaces.get(typefaceName);

    }



    /**
     * @param pActivity
     * @param pView     or null
     */
    public static void showKeyboard(Activity pActivity, View pView) {
        if (pView == null) {
            pView = pActivity.getWindow().getCurrentFocus();
        } else {
            /**
             * For {@link EditText}, a call to {@link View#requestFocus()} will
             * open the keyboard as per inputType set for {@link EditText}
             */
            pView.requestFocus();
        }
        if (pView != null) {
            InputMethodManager imm = (InputMethodManager) pActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(pView, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    /**
     * @param pView
     * @param pActivity
     */
    public static void hideKeyboard(View pView, Activity pActivity) {
        if (pView == null) {
            pView = pActivity.getWindow().getCurrentFocus();
        }
        if (pView != null) {
            InputMethodManager imm = (InputMethodManager) pActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(pView.getWindowToken(), 0);
            }
        }
    }


    /**
     * this method gives screen size inlcuding status bar height.
     * for statusbarHeight use getStatusBarHeight(Activity pActivity)
     *
     * @param pActivity
     * @return
     */
    public static Point getScreenSize(Activity pActivity) {
        Point outSize = new Point();
        pActivity.getWindowManager().getDefaultDisplay().getSize(outSize);
        return outSize;
    }


    /**
     * @param pText
     * @return true if either pText is null or if it only has spaces
     */
    public static boolean isBlank(String pText) {
        return pText == null || pText.trim().length() == 0;
    }

    public static int getRelativeTop(View pView) {
        if (pView.getParent() == pView.getRootView()){
            return pView.getTop();
        }
        else {
            return pView.getTop() + getRelativeTop((View) pView.getParent());
        }
    }

    public static int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    public static String loadJSONFromAsset(Context pContext , String pJsonFile) {
        String json = null;
        try {
            InputStream is = pContext.getAssets().open(pJsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
