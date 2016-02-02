package utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.gulshan.loginflow.views.listeners.RequestListener;
import com.example.gulshan.loginflow.views.listeners.VolleyErrorListenerImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gulshan on 31/1/16.
 */
public class ApiController {
    private static final String LOG_TAG = ApiController.class.getSimpleName();
    private static RequestQueue mRequestQueue;
    private static final RetryPolicy RETRY_POLICY_GET = new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    private static final RetryPolicy RETRY_POLICY_GET_SHORT = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    private static final RetryPolicy RETRY_POLICY_POST = new DefaultRetryPolicy(80000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    private static final RetryPolicy RETRY_POLICY_POST_SHORT = new DefaultRetryPolicy(5000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    private static final NetworkResponse RESPONSE_NO_INTERNET = new NetworkResponse(1025, "Network Not Available".getBytes(), null, false);
    // volley objects - end

    public static final void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        PrefUtils.init(context);
    }

    /**
     * @param pRequest
     * @param pContext
     */
    private static void dispatchToQueue(Request<String> pRequest, Context pContext, RetryPolicy retryPolicy) {
        if (!NetworkUtils.isNetworkConnected(pContext) && pRequest.getMethod() != Request.Method.GET) {
            // non-GET request should not return cached response in case of no network
            pRequest.deliverError(new VolleyError(RESPONSE_NO_INTERNET));
            return;
        }
        switch (pRequest.getMethod()) {
            case Request.Method.POST: {
                if (retryPolicy == null) {
                    pRequest.setRetryPolicy(RETRY_POLICY_POST);
                } else {
                    pRequest.setRetryPolicy(retryPolicy);
                }
                break;
            }
            case Request.Method.GET:
            default: {
                if (retryPolicy == null) {
                    pRequest.setRetryPolicy(RETRY_POLICY_GET);
                } else {
                    pRequest.setRetryPolicy(retryPolicy);
                }
                break;
            }
        }
        mRequestQueue.add(pRequest);
    }

    private static Request<String> bundleToVolleyRequestNoCaching(
            final Context context, int request_method_type,
            final Object newRequest, String url, final RequestListener mListener) {
        // start code for logging and analytics
//        if (AppConfig.DEBUG) {
            Log.d(LOG_TAG, "req obj: " + JsonUtils.jsonify(newRequest));
//        }
        StringBuffer buffer = new StringBuffer(url);
        buffer.replace(0, UrlResolver.BASE_URL.length() - 1, "response--");
        final String url_recieved = buffer.toString();
        // end
        Request<String> tempRequest = new JsonRequest<String>(
                request_method_type, url, JsonUtils.jsonify(newRequest),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        if (AppConfig.DEBUG) {
                            Log.d(LOG_TAG, "URL: " + url_recieved);
                            Log.d(LOG_TAG, "Response: " + response);
//                        }
                    }
                },
                new VolleyErrorListenerImpl(context, url_recieved, mListener)) {
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                Response<String> mResponse;
                if (response.statusCode == 200) {
                    String responseBody = new String(response.data);
                    if (mListener != null)
                        mListener.onRequestCompleted(responseBody);
                    mResponse = Response.success(responseBody,
                            HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    parseNetworkError(new VolleyError(response));
                    mResponse = Response.error(new VolleyError(response));
                }
                return mResponse;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headersMap = new HashMap<String, String>();
                return headersMap;
            }

        };

        return tempRequest;
    }

    public static void requestOtp(Map<String, String> params,
                                  final Context pContext, RequestListener pListener, Response.ErrorListener errorListener) {
        String url = UrlResolver
                .withAppendedPath(UrlResolver.EndPoints.REQUEST_OTP);
//        Request<String> volleyTypeRequest = bundleToVolleyRequestNoCaching(
//                pContext, Request.Method.POST, pOtpRequest, url, pListener);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, pListener, errorListener);

        pListener.onRequestStarted();
        jsObjRequest.setTag(pListener);
        jsObjRequest.setShouldCache(false);
        jsObjRequest.setRetryPolicy(RETRY_POLICY_POST);
        mRequestQueue.add(jsObjRequest);
//        dispatchToQueue(volleyTypeRequest, pContext,RETRY_POLICY_POST);
    }

    public static void login(Map<String, String> params,
                             final Context pContext, RequestListener pListener, Response.ErrorListener errorListener) {
        String url = UrlResolver
                .withAppendedPath(UrlResolver.EndPoints.LOGIN);
//        Request<String> volleyTypeRequest = bundleToVolleyRequestNoCaching(
//                pContext, Request.Method.POST, pOtpRequest, url, pListener);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, pListener, errorListener);

        pListener.onRequestStarted();
        jsObjRequest.setTag(pListener);
        jsObjRequest.setShouldCache(false);
        jsObjRequest.setRetryPolicy(RETRY_POLICY_POST);
        mRequestQueue.add(jsObjRequest);
    }

}
