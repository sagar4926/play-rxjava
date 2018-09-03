package com.soum4j.learn.networks;

import com.google.gson.annotations.SerializedName;
import com.soum4j.learn.models.blueprints.JsonModel;

public class Address extends JsonModel {
    @SerializedName("street")
    public String street;
    @SerializedName("suite")
    public String suite;
    @SerializedName("city")
    public String city;
    @SerializedName("zipcode")
    public String zipcode;
    @SerializedName("geo")
    public Geo geo;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
