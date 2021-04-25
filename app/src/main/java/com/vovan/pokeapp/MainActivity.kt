package com.vovan.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.library.BuildConfig
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.navHost, TitleFragment())
            .commit()
    }
}