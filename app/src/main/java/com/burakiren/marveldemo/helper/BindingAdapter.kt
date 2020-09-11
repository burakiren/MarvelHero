package com.burakiren.marveldemo.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind:imgUrl")
fun setProfilePicture(imageView: ImageView, imgUrl: String?) {
    Glide.with(imageView.context).load(imgUrl).into(imageView)
}
