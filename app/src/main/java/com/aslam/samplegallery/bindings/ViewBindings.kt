package com.aslam.samplegallery.bindings

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("loadImage")
fun loadImageWithGlide(view: AppCompatImageView,url:String) {
     Glide.with(view.context)
                .load(url)
                .into(view)
}