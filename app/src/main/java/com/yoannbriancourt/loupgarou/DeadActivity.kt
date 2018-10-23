package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dead.*

class DeadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dead)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            // Si c'est le début du jour
            if(1==1){
                val intent = Intent(this, ChronosActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, NightActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
