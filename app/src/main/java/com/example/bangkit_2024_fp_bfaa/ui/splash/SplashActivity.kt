package com.example.bangkit_2024_fp_bfaa.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bangkit_2024_fp_bfaa.R
import com.example.bangkit_2024_fp_bfaa.ui.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this, HomeActivity::class.java).also {
            runBlocking {
                delay(500)
                startActivity(it)
                finish()
            }
        }
    }
}