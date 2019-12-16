package com.razzdrawon.urbandictionary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.razzdrawon.urbandictionary.R
import com.razzdrawon.urbandictionary.view.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
