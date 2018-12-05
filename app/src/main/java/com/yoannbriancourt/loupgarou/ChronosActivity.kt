package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_chronos.*
import kotlinx.android.synthetic.main.content_chronos.*
import java.util.*

class ChronosActivity : AppCompatActivity() {
    private val timer = Timer()
    private var timerTask: TimerTask? = null
    private val handler = Handler()
    private var time : Int = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chronos)
        setSupportActionBar(toolbar)

        chronos.text = time.toString()

        startTimer()

        addTime.setOnClickListener {
            this.time += 60
        }
        fab.setOnClickListener {
            stopTimer()
            val intent = Intent(this, VoteActivity::class.java)
            startActivity(intent)
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.stopTimer()
            GameEngine.restart()
            finishAffinity()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.clickBack), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    //To start timer
    private fun startTimer() {
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    if(time > 0){
                        time--
                        chronos.text = time.toString()
                    }else{
                        stopTimer()
                        fab.callOnClick()
                    }
                }
            }
        }
        timer.schedule(timerTask, 0, 1000)
    }

    private fun stopTimer(){
        timer.cancel()
        timer.purge()
    }
}

