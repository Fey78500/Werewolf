package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dead.*
import kotlinx.android.synthetic.main.content_dead.*

class DeadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dead)
        setSupportActionBar(toolbar)
        var deadPlayers = GameEngine.getDeadPlayer()
        if(deadPlayers.size != 0){
            var str = ""
            for(deadPlayer in deadPlayers){
                str += deadPlayer.name + ", "
            }
            str.removeSuffix(", ")
            textDied.text = "Those players died : $str"
        }else{
            textDied.text = "No one died"
        }

        fab.setOnClickListener {
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

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

}
