package com.fauzan.favoriteuserapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.fauzan.favoriteuserapp.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val toolbar: Toolbar = findViewById(R.id.toolbarabout)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            title = getString(R.string.about)
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }
        }

        //Set Link
        learn_more.setOnClickListener {
            val link = "https://github.com/about/diversity"
            val linkClick = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(linkClick)
        }
    }
}