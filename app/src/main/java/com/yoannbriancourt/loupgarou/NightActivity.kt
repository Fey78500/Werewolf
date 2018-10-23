package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yoannbriancourt.loupgarou.model.Villager
import kotlinx.android.synthetic.main.activity_night.*

class NightActivity : AppCompatActivity() {
    private var players : ArrayList<Villager> = GameEngine.getPlayers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            // Si il n'y plus de joueur, la journé arrive
            if(1==1){
                val intent = Intent(this, DeadActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, NightActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
