package com.gamechangesolutions.assignment.utils;

import androidx.room.TypeConverter;

import com.gamechangesolutions.assignment.model.Comment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * {@link CommentListConverter} is used for room entity to convert String from/to {@link Comment} data type
 */
public class CommentListConverter {
    @TypeConverter
    public static ArrayList<Comment> fromString(String value) {
        Type userType = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        return new Gson().fromJson(value, userType);
    }

    @TypeConverter
    public static String fromCommentList(ArrayList<Comment> commentList) {
        return new Gson().toJson(commentList);
    }
}
