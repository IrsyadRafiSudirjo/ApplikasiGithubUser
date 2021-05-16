package com.example.applikasigithubuser2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.applikasigithubuser2.preferencefragment.PreferenceFragment

class SettingActivity : AppCompatActivity() {

    companion object {
        const val halo = "halo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, PreferenceFragment())
            .commit()
    }
}