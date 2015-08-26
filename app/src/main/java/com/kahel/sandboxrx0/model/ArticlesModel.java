package com.kahel.sandboxrx0.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Mark on 8/18/2015.
 */
public class ArticlesModel {
    @SerializedName("total_pages") private int total_pages;
    @SerializedName("current_page") private int current_page;
    @SerializedName("total_records") private int total_records;
    @Getter @SerializedName("data") private ArrayList<HashMap<String, String>> data;

}
