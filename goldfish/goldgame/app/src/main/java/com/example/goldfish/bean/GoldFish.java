package com.example.goldfish.bean;

import java.util.List;

/**
 * goldfish picture
 */
public class GoldFish {
    private String name;
    private List<String> pictureset;
    private String tileback;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPictureset(List<String> pictureset) {
        this.pictureset = pictureset;
    }
    public List<String> getPictureset() {
        return pictureset;
    }

    public void setTileback(String tileback) {
        this.tileback = tileback;
    }
    public String getTileback() {
        return tileback;
    }
}
