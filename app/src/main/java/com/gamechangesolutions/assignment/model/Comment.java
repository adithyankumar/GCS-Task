package com.gamechangesolutions.assignment.model;

import com.gamechangesolutions.assignment.utils.Utils;

import java.io.Serializable;

public class Comment implements Serializable {
    private String body;
    private User user;

    public Comment() {

    }

    public String getBody() {
        return Utils.removeHtmlTags(body);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
