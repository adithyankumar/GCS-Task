package com.gamechangesolutions.assignment.model;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gamechangesolutions.assignment.utils.TimestampConverter;
import com.gamechangesolutions.assignment.utils.UserConverter;
import com.gamechangesolutions.assignment.utils.Utils;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "issue_table")
public class Issue implements Serializable {
    private int id;
    @PrimaryKey
    private int number;
    private String title;
    private String state;
    private int comments;
    private String created_at;
    @TypeConverters(TimestampConverter.class)
    private Date updated_at;
    private String body;
    @TypeConverters(UserConverter.class)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getBody() {
        return Utils.removeHtmlTags(body);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
        //return new Gson().fromJson(user, User.class);
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param textView
     * @param issue    display user name bigger and bolder and update time smaller in same textView
     */
    @BindingAdapter({"issue"})
    public static void formatText(TextView textView, Issue issue) {
        SpannableString userName = new SpannableString(issue.user.getLogin());
        SpannableString updatedDate = new SpannableString(issue.updated_at.toString());
        userName.setSpan(new RelativeSizeSpan(1f), 0, userName.length(), 0); // set size
        userName.setSpan(new StyleSpan(Typeface.BOLD), 0, userName.length(), 0); // set size
        updatedDate.setSpan(new RelativeSizeSpan(0.5f), 0, updatedDate.length(), 0); // set size
        textView.setText(TextUtils.concat(userName, "\n", updatedDate));
    }
}
