package com.example.gulshan.loginflow.views.models;

/**
 * Created by gulshan on 30/1/16.
 */
public class Country {
    private String dial_code;
    private String name;
    private String code;

    public String getCode() {return code;}
    public String getDial_code() {
        return this.dial_code;
    }
    public void setDial_code(String dial_code) {
        this.dial_code = dial_code;
    }

    public void setCode(String isd_code) {
        this.dial_code = isd_code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
