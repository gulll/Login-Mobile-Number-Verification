package com.example.gulshan.loginflow.views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gulshan.loginflow.R;
import com.example.gulshan.loginflow.views.models.Country;

import java.util.List;

/**
 * Created by gulshan on 31/1/16.
 */
public class SpinnerCountryAdapter extends ArrayAdapter {
    private List<Country> mCountryList;
    private Context mContext;

    public SpinnerCountryAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mCountryList = objects;
        mContext = context;
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {

// Inflating the layout for the custom Spinner
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.item_spinner, parent, false);

// Declaring and Typecasting the textview in the inflated layout
        TextView tvCountryISD = (TextView) layout
                .findViewById(R.id.tv_country_isd);
        TextView tvCountryName = (TextView) layout
                .findViewById(R.id.tv_country_name);

// Setting the text using the array
        tvCountryName.setText(mCountryList.get(position).getName());
        tvCountryISD.setText(mCountryList.get(position).getDial_code());

//        if (position == 0) {
//// Removing the image view
//            img.setVisibility(View.GONE);
//// Setting the size of the text
//            tvLanguage.setTextSize(20f);
//// Setting the text Color
//
//        }

        return layout;
    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}

