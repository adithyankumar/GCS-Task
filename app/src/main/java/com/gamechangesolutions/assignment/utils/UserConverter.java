package com.gamechangesolutions.assignment.utils;

import androidx.room.TypeConverter;

import com.gamechangesolutions.assignment.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * {@link UserConverter} is used for room entity to convert String from/to {@link User} data type
 */
public class UserConverter {
    @TypeConverter
    public static User fromString(String value) {
        Type userType = new TypeToken<User>() {
        }.getType();
        return new Gson().fromJson(value, userType);
    }

    @TypeConverter
    public static String fromUser(User user) {
        return new Gson().toJson(user);
    }
}
