package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.yoannbriancourt.loupgarou.model.Villager
import kotlinx.android.synthetic.main.activity_end.*
import kotlinx.android.synthetic.main.content_end.*

class EndActivity : AppCompatActivity() {
    private lateinit var players : ArrayList<Villager>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
        setSupportActionBar(toolbar)

        this.players = GameEngine.getAllPlayers()
        getPlayers()

        fab.setOnClickListener {
            GameEngine.restart()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getPlayers(){
        for(player in players) {
            val button = Button(this)
            button.text = getString(R.string.buttonPlayerWithRole,player.name,player.javaClass.simpleName)
            if(player.health < 1){
                button.background = getDrawable(R.drawable.button_dead)
            }
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutEnd.addView(button)
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

}
