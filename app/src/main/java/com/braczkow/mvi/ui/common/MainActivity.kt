package com.braczkow.mvi.ui.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.braczkow.mvi.R
import com.braczkow.mvi.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_frame, MainFragment())
                    .commit()
        }
    }
}
