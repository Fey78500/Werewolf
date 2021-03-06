package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dead.*
import kotlinx.android.synthetic.main.content_dead.*

class DeadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dead)
        setSupportActionBar(toolbar)

        checkWinning()

        fab.setOnClickListener {
            // Si c'est le début du jour
            if(GameEngine.getDay()){
                GameEngine.setDay(false)
                val intent = Intent(this, ChronosActivity::class.java)
                startActivity(intent)
            }else{
                GameEngine.setDay(true)
                val intent = Intent(this, NightActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            GameEngine.restart()
            finishAffinity()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.clickBack), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun checkWinning(){
        val deadPlayers = GameEngine.getDeadPlayer()
        val end = GameEngine.checkWinning()
        if(end != "continue"){
            textDied.text = getString(R.string.end,end)
            fab.visibility = View.INVISIBLE
            Handler().postDelayed({
                val intent = Intent(this, EndActivity::class.java)
                startActivity(intent)
            }, 5000)

        }else{
            if(deadPlayers.size != 0){
                var str = ""
                for(deadPlayer in deadPlayers){
                    str += " " + deadPlayer.name + ","
                }
                str.trimEnd(',')
                textDied.text = getString(R.string.dead,str)
            }else{
                textDied.text = getString(R.string.noOneDied)
            }
        }
    }

}
