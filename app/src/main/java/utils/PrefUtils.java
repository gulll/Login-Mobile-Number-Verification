package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gulshan on 31/1/16.
 */
public class PrefUtils {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private static String prefName = "user_pref";
    private static final String KEY_APP_ACCESS_TOKEN = "appAccessToken";
    /**
     * @param ctx
     */
    public static void init(Context ctx) {
        pref = ctx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public static final String getAppAccessToken() {
        return pref.getString(KEY_APP_ACCESS_TOKEN, "");
    }
    public static final void saveAppAccessToken(String pAccessToken) {
        editor.putString(KEY_APP_ACCESS_TOKEN, pAccessToken);
        editor.apply();
    }
}
