package com.brunowcnascimento.projectpoc.feature.font_size

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brunowcnascimento.projectpoc.R

class FontSizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_size)
    }

    companion object {
        fun newInstance() = FontSizeActivity()
    }
}