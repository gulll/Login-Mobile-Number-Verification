package com.example.gulshan.loginflow.views.models.ApiModels;

/**
 * Created by gulshan on 31/1/16.
 */
public class OtpRequest {
    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    private String mobile_no;
    private String country_code;
    private String country_name;
}
