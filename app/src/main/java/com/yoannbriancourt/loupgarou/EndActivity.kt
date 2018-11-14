package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
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
            button.text = "${player.name} : ${player.javaClass.simpleName}"
            if(player.health == 0){
                button.background = getDrawable(R.drawable.button_dead)
            }
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutEnd.addView(button)
        }
    }

}
