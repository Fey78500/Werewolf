package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_vote.*

class VoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            // Si il n'y plus de joueur, le vote se termine arrive
            if(1==1){
                val intent = Intent(this, DeadActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, VoteActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
