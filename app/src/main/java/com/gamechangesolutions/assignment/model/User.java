package com.gamechangesolutions.assignment.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gamechangesolutions.assignment.R;

import java.io.Serializable;

public class User implements Serializable {
    private String login;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }


    /**
     * @param imageView
     * @param url       load the image from url and display in imageView
     */
    @BindingAdapter({"avatar_url"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().circleCrop())
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView);

    }

}
