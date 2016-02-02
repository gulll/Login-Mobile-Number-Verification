package utils;

import android.util.Log;

import java.lang.reflect.Type;
import com.google.gson.Gson;

/**
 * Created by gulshan on 30/1/16.
 */
public final class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    private static Gson M_GSON = new Gson();

    public static String jsonify(Object object) {
        return M_GSON.toJson(object);
    }

    /**
     * @param pJson
     * @param pType
     * @param <T>
     * @return
     */
    public static <T> T objectify(String pJson, Class<T> pType) {
        if (pJson == null || pJson.trim().length() == 0) {
            return null;
        }
        try {
            return M_GSON.fromJson(pJson, pType);
        } catch (Exception e) {
            Log.e(LOG_TAG, "objectify() Class " + pType + ", Json: " , e);
        }
        return null;
    }

    /**
     * @param pJson
     * @param pType
     * @param <T>
     * @return
     */
    public static <T> T objectify(String pJson, Type pType) {
        if (pJson == null || pJson.trim().length() == 0) {
            return null;
        }
        try {
            return M_GSON.fromJson(pJson, pType);
        } catch (Exception e) {
            Log.e(LOG_TAG, "objectify() Type: " + pType + ", Json: " + pJson, e);
        }
        return null;
    }
}