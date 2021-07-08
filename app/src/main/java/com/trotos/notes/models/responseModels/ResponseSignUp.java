package com.trotos.notes.models.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trotos.notes.models.User;

public class ResponseSignUp {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("User")
    @Expose
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}