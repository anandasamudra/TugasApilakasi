package com.mylist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.mylist.MainActivity
import com.mylist.R

class SplassScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //transparns actionbar material1
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_splass_screen)
        //transparns actionbar material1
        supportActionBar?.hide()
        val handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.animasimasuk, R.anim.animasikeluar)
            finish()
        }, 3000)
    }
}