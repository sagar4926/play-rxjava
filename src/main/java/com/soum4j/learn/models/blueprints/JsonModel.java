package com.soum4j.learn.models.blueprints;

import com.google.gson.Gson;

public class JsonModel {
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
