package com.example.goldfish;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.lang.reflect.Type;


public class GsonUtils {

    private static Gson sGson = null;
    private static JsonParser sParser = new JsonParser();

    public static Gson getInstance() {
        if (sGson == null) {
            sGson = new GsonBuilder().create();
        }
        return sGson;
    }

}
