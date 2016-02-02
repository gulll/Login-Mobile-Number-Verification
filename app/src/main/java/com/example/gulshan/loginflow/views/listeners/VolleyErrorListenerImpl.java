package com.example.gulshan.loginflow.views.listeners;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.example.gulshan.loginflow.views.config.Constants;

import utils.NetworkUtils;
import utils.UrlResolver;

/**
 * Created by gulshan on 31/1/16.
 */
public class VolleyErrorListenerImpl implements ErrorListener {

	private final static String LOG_TAG = VolleyErrorListenerImpl.class.getSimpleName();

	private final static String CATEGORY_ERROR = "CATEGORY_ERROR";

	private Context mContext;
	private String mApiActionPath;
	private RequestListener mRequestListener;

	/**
	 * @param pContext
	 * @param pRequestListener
	 */
	public VolleyErrorListenerImpl(Context pContext, String pApiActionPath, RequestListener pRequestListener) {
		this.mContext = pContext;
		this.mApiActionPath = pApiActionPath;
		this.mRequestListener = pRequestListener;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Log.e(LOG_TAG, mApiActionPath);

		int errorCode;
		String message;

		if (!NetworkUtils.isNetworkConnected(mContext)) {
			Log.e(LOG_TAG, "Error: " + error);
			errorCode = Constants.ERROR_CODES.NO_INTERNET;
			message = "Network Not Available";
		} else if (error == null || error.networkResponse == null) {
			Log.e(LOG_TAG, "Error: " + error);
			errorCode = Constants.ERROR_CODES.SHIT_HAPPENED;
			message = "Error object is null";
		} else {
			errorCode = error.networkResponse.statusCode;
			if (error.networkResponse.data != null) {
				message = new String(error.networkResponse.data);
			} else {
				message = "Response data is null";
			}
		}

		Log.e(LOG_TAG, "Error: " + errorCode + ", " + message);

		if (mRequestListener != null) {
			mRequestListener.onRequestError(errorCode, message);
		}
//		Controller.logApiErrorOnGA(CATEGORY_ERROR, mApiActionPath, message, errorCode);
	}
}
