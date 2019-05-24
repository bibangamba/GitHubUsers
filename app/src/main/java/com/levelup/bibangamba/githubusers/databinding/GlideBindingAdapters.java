package com.levelup.bibangamba.githubusers.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.levelup.bibangamba.githubusers.R;

public class GlideBindingAdapters {
    @BindingAdapter("profilePictureURL")
    public static void setProfilePicture(ImageView imageView, String imageURL) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(imageURL)
                .into(imageView);
    }


    @BindingAdapter({"requestListener", "profilePictureURL"})
    public static void setProfilePicture(ImageView imageView,
                                         RequestListener<Object> listener,
                                         String imageURL) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(imageView.getContext())
                .addDefaultRequestListener(listener)
                .setDefaultRequestOptions(options)
                .load(imageURL)
                .into(imageView);
    }
}
