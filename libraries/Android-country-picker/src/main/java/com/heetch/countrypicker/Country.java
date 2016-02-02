package com.heetch.countrypicker;

/**
 * Created by GODARD Tuatini on 07/05/15.
 */
public class Country {
    private String isoCode;
    private String dialingCode;
    private String name;

    public Country() {

    }

    public Country(String isoCode, String dialingCode,String name) {
        this.isoCode = isoCode;
        this.dialingCode = dialingCode;
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
