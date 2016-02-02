package com.example.gulshan.loginflow.views.activities.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gulshan.loginflow.BuildConfig;
import com.example.gulshan.loginflow.R;
import com.example.gulshan.loginflow.views.Adapters.TutorialPagerAdapter;
import com.heetch.countrypicker.CountryPickerCallbacks;
import com.heetch.countrypicker.CountryPickerDialog;

import utils.ApiController;


public class LoginActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnTouchListener ,View.OnClickListener{

    private static final String LOG_TAG = LoginActivity.class.getSimpleName() ;
    private int[] mImageResources = {
            R.mipmap.tour_image,
            R.mipmap.tour_image,
            R.mipmap.tour_image,
            R.mipmap.tour_image,
    };
    private ViewPager mTutorialPager;
    private LinearLayout mPagerIndicator;
    private TutorialPagerAdapter mAdapter;
    private int dotsCount;
    private ImageView[] dots;
    private TextView mTvCountryName;
    private TextView mTvCountryCode;
    private EditText mTextPhone;
    private Button mBtnStart;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ApiController.init(this);
        mTutorialPager = (ViewPager) findViewById(R.id.pager_introduction);
        mAdapter = new TutorialPagerAdapter(this, mImageResources);
        mTutorialPager.setAdapter(mAdapter);
        mTutorialPager.setCurrentItem(0);
        mTutorialPager.addOnPageChangeListener(this);
        mPagerIndicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mTvCountryName = (TextView) findViewById(R.id.tv_country_name);
        mTvCountryCode = (TextView) findViewById(R.id.tv_country_isd);
        mBtnStart = (Button) findViewById(R.id.btn_get_started);
        mTextPhone = (EditText) findViewById(R.id.edt_phone_number);
        setUiPageViewController();
        mTvCountryName.setOnTouchListener(this);
        mTvCountryCode.setOnTouchListener(this);
        mBtnStart.setOnClickListener(this);
        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();
        Log.d(LOG_TAG,"My Number is "+mPhoneNumber);
        if(mPhoneNumber != null) {
            mTextPhone.setText(mPhoneNumber);
        }
    }

    public void handleCountryPick() {

        CountryPickerDialog countryPicker =
                new CountryPickerDialog(this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(com.heetch.countrypicker.Country country, int flagResId) {
                        mTvCountryName.setText(country.getName());
                        mTvCountryCode.setText("+"+country.getDialingCode());

                    }
                });
        countryPicker.show();

    }


    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            mPagerIndicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_item_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < dotsCount; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_selected_item_dot,this.getTheme() ));
            }else {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_selected_item_dot));
            }
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_item_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handleCountryPick();
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_get_started) {
            if(!checkAssertion()) {
                Toast.makeText(getApplicationContext(),"Please Check your Mobile number and Country is correct !",Toast.LENGTH_LONG).show();
                return;
            }
            Intent verificationIntent = new Intent(this,LoginVerficationActivity.class);
            verificationIntent.putExtra("phone_number",""+mTextPhone.getText());
            verificationIntent.putExtra("country_name",""+mTvCountryName.getText());
            verificationIntent.putExtra("country_code", ""+mTvCountryCode.getText());
            startActivity(verificationIntent);
        }
    }

    private boolean checkAssertion() {
        if(mTvCountryCode.getText().length() >0 && mTextPhone.getText().length() == 10) {
            return true;
        }
        return false;
    }
}
