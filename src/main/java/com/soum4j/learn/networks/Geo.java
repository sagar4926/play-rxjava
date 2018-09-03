package com.soum4j.learn.networks;

import com.google.gson.annotations.SerializedName;
import com.soum4j.learn.models.blueprints.JsonModel;

public class Geo extends JsonModel {

    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
