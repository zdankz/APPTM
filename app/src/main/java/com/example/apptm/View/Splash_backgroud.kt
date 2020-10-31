package com.example.apptm.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apptm.MainActivity
import com.example.apptm.R
import kotlinx.android.synthetic.main.activity_splash_backgroud.*

class Splash_backgroud : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_backgroud)
        iv_note.alpha= 0f
        iv_note.animate().setDuration(4000).alpha(1f).withEndAction{
            val i = Intent(this, login::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}
