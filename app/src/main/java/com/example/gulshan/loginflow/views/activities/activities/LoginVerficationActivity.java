package com.example.gulshan.loginflow.views.activities.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.gulshan.loginflow.R;
import com.example.gulshan.loginflow.views.listeners.RequestListener;
import com.example.gulshan.loginflow.views.models.ApiModels.OtpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utils.ApiController;
import utils.MyUtilities;
import utils.PrefUtils;

/**
 * Created by gulshan on 31/1/16.
 */
public class LoginVerficationActivity extends AppCompatActivity implements RequestListener{
    private static final String LOG_TAG = LoginVerficationActivity.class.getSimpleName();
    private ImageView img_background;
    private Response.ErrorListener mErrorListener;
    private IncomingSms smsReceiver;
    private String mOtp;
    private Map<String, String> paramsOTP;
    private EditText mEdtOtp1;
    private EditText mEdtOtp2;
    private EditText mEdtOtp3;
    private EditText mEdtOtp4;
    private ProgressDialog mProgressDialogDownload;
    private boolean mLogin = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verification);
        OtpRequest otpModel = new OtpRequest();
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgressBar();
                Toast.makeText(getApplicationContext(),"Some Error Occurred Please try Again",Toast.LENGTH_LONG).show();
            }
        };
        otpModel.setMobile_no(getIntent().getStringExtra("phone_number"));
        otpModel.setCountry_name(getIntent().getStringExtra("country_name"));
        otpModel.setCountry_code(getIntent().getStringExtra("country_code"));
        paramsOTP = new HashMap<String, String>();
        paramsOTP.put("mobile_no", getIntent().getStringExtra("phone_number"));
        paramsOTP.put("country_name", getIntent().getStringExtra("country_name"));
        paramsOTP.put("country_code",getIntent().getStringExtra("country_code"));
        img_background = (ImageView) findViewById(R.id.img_bg);
        mProgressDialogDownload = new ProgressDialog(this);
        mProgressDialogDownload.getWindow().setGravity(Gravity.BOTTOM);
        mProgressDialogDownload.setCanceledOnTouchOutside(false);
        String phone = paramsOTP.get("mobile_no");
        if(phone != null) {
            ((TextView) findViewById(R.id.tv_mobile_digit)).setText(getString(R.string.we_have_send,
                    phone.substring(5, phone.length())));
        }
        smsReceiver = new IncomingSms();
        requestOTP();
        mEdtOtp1 = (EditText) findViewById(R.id.edt_otp1);
        mEdtOtp2 = (EditText) findViewById(R.id.edt_otp2);
        mEdtOtp3 = (EditText) findViewById(R.id.edt_otp3);
        mEdtOtp4 = (EditText) findViewById(R.id.edt_otp4);
        mEdtOtp1.requestFocus();
        setTextChangeListeners();
        ( findViewById(R.id.ll_resend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestOTP();
            }
        });

    }

    private void setTextChangeListeners() {
        mEdtOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEdtOtp1.getText().length() == 1) {
                    mEdtOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEdtOtp2.getText().length() == 1) {
                    mEdtOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mEdtOtp3.getText().length() == 1) {
                    mEdtOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEdtOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEdtOtp4.getText().length() == 1) {
//                    mEdtOtp2.requestFocus();.
                    mOtp = mEdtOtp1.getText().toString() + mEdtOtp2.getText().toString() + mEdtOtp3.getText().toString() + mEdtOtp4.getText().toString();
                    if (mOtp.length() == 4) {
                        gotoLogin();
                    } else {
                        if (mEdtOtp1.getText().length() == 0) {
                            mEdtOtp1.requestFocus();
                        } else if (mEdtOtp2.getText().length() == 0) {
                            mEdtOtp2.requestFocus();
                        } else if (mEdtOtp3.getText().length() == 0) {
                            mEdtOtp3.requestFocus();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void requestOTP() {
        ApiController.requestOtp(paramsOTP, this, this, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

    }

    @Override
    public void onRequestStarted() {
        mProgressDialogDownload.show();

    }

    @Override
    public void onRequestCompleted(Object responseObject) {
        if(isFinishing()) {
            return;
        }
        hideProgressBar();
        try {
            int status = (int) new JSONObject(responseObject.toString()).get("status");
            if(status >0 ) {
                JSONObject sessionData = new JSONObject(new JSONObject(responseObject.toString()).getString("data"));
                Log.d(LOG_TAG, "response is " + sessionData);
                paramsOTP.put("session_id", sessionData.getString("session_id"));
            }else {
                String message = new JSONObject(responseObject.toString()).getString("message");
                Toast.makeText(getApplication(),""+message,Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
//            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IntentFilter intentFilterSMS = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
                registerReceiver(smsReceiver, intentFilterSMS);
                img_background.setImageResource(R.drawable.otp_bg);
                findViewById(R.id.root_otp_card).setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void onRequestError(int errorCode, String message) {

    }


    private class IncomingSms extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (Object aPdusObj : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();
//                        if (phoneNumber.contains("CUROFY")) {

                            // If number of digits changes in code
                            //Change here
                            if ( message.toUpperCase().contains("access".toUpperCase()) && message.toUpperCase().contains("curofy".toUpperCase())) {
                                mOtp = "";
                                for(int i =0;i<message.length();i++) {
                                    if(message.charAt(i) >='0' && message.charAt(i) <= '9') {
                                        mOtp+=message.charAt(i);
                                    }
                                }
                                if(mOtp.length() == 4) {
                                    //Success
                                    Log.d(LOG_TAG, "OTP is " + mOtp);
                                    gotoLogin();

                                }
                            }
//                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void gotoLogin() {
        if(mOtp == null && mOtp.length() != 4) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    MyUtilities.hideKeyboard(mEdtOtp4,LoginVerficationActivity.this);
                    if (mEdtOtp1.getText().length() == 0) {
                        mEdtOtp1.setText(String.valueOf(mOtp.charAt(0)));
                        mEdtOtp2.setText(String.valueOf(mOtp.charAt(1)));
                        mEdtOtp3.setText(String.valueOf(mOtp.charAt(2)));
                        mEdtOtp4.setText(String.valueOf(mOtp.charAt(3)));
                    }
                }
            }
        });

        Log.d(LOG_TAG,"OTP is "+mOtp+" accessToken is "+ paramsOTP.get("session_id"));
        paramsOTP.put("otp",mOtp);
        if(paramsOTP.get("session_id") != null) {
            ApiController.login(paramsOTP, this, new RequestListener() {
                @Override
                public void onRequestStarted() {
                    if(mLogin) {
                        return;
                    }
                    mProgressDialogDownload.show();
                }

                @Override
                public void onRequestCompleted(Object responseObject) {
                    if(isFinishing()) {
                        return;
                    }
                    hideProgressBar();
                    if(mLogin) {
                        return;
                    }

                    try {
                        int status = (int) new JSONObject(responseObject.toString()).get("status");
                        if(status >0 ) {
                            JSONObject sessionData = new JSONObject(new JSONObject(responseObject.toString()).getString("data"));
                            Log.d(LOG_TAG, "response is " + sessionData);
                            PrefUtils.saveAppAccessToken(sessionData.getString("session_id"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    unregisterReceiver(smsReceiver);
                                    smsReceiver = null;
                                    img_background.setImageResource(R.drawable.profile);
                                    Toast.makeText(getApplication(), "Success Login", Toast.LENGTH_LONG).show();
                                    mLogin = true;
                                    findViewById(R.id.root_otp_card).setVisibility(View.GONE);
                                }
                            });
                        }else {
                            String message = new JSONObject(responseObject.toString()).getString("message");
                            Toast.makeText(getApplication(),""+message,Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onRequestError(int errorCode, String message) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    hideProgressBar();
                    Toast.makeText(getApplicationContext(),"Some Error Occurred Please try Again",Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(this, "Please retry to using your mobile number!", Toast.LENGTH_LONG).show();
        }
    }

    public void hideProgressBar() {
        if (mProgressDialogDownload.isShowing()) {
            mProgressDialogDownload.dismiss();
            mProgressDialogDownload.setOnKeyListener(null);
            mProgressDialogDownload.setOnDismissListener(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (smsReceiver != null) {
            // in case we have not register receiver
            try {
                unregisterReceiver(smsReceiver);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
