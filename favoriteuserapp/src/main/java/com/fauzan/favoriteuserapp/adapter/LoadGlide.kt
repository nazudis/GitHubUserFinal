package com.fauzan.favoriteuserapp.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(link: String?){
    Glide.with(this.context)
        .load(link)
        .into(this)
}