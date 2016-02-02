package utils;

import android.util.SparseArray;

/**
 * Created by gulshan on 31/1/16.
 */
public final class UrlResolver {
    public static SparseArray<String> endPointMapper = null;

    public static String BASE_URL = "http://staging-curofy-com-xq8gq5ri2r77.runscope.net/";

    /**
     * @param pEndPoint
     * @return
     */
    public static String withAppendedPath(int pEndPoint) {
        if (endPointMapper == null) {
            populateMapper();
        }
        return BASE_URL + endPointMapper.get(pEndPoint);
    }

    /**
     *
     */
    private static void populateMapper() {
        endPointMapper = new SparseArray<>();
        endPointMapper.put(EndPoints.REQUEST_OTP, "generate_otp.json");// register
        endPointMapper.put(EndPoints.LOGIN, "login_app.json");

    }

    public static class EndPoints {
        public static final int NONE = -1;
        public static final int REQUEST_OTP = 0;
        public static final int LOGIN = 1;
    }
}