package com.kahel.sandboxrx0.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Mark on 8/25/2015.
 */
@Data
public class FacebookProfileModel {
    @SerializedName("id") private String id;
    @SerializedName("first_name") private String firstName;
    @SerializedName("last_name") private String lastName;
    @SerializedName("email") private String email;
}
