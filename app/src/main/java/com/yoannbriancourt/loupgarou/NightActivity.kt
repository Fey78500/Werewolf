package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.yoannbriancourt.loupgarou.model.Villager
import kotlinx.android.synthetic.main.activity_night.*
import kotlinx.android.synthetic.main.content_night.*

class NightActivity : AppCompatActivity() {
    private var turn : Int = GameEngine.GetTurn()
    private val player : Villager = GameEngine.GetPlayer(turn)
    private val players : ArrayList<Villager> = GameEngine.GetPlayers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night)
        setSupportActionBar(toolbar)

        //set les textes en fonction du player
        username.setText(player.name)
        role.setText(player.javaClass.simpleName)

        if(player.javaClass.simpleName != "Villager"){
            // Affiche les actions possible en fonction du role
            /*for(action in player){
                val button = Button(this)
                button.text = action.name
                button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                layoutAction.addView(button)
            }*/
            // Affiche les joueurs vivants
            for(otherPlayer in players) {
                val button = Button(this)
                button.text = otherPlayer.name
                button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                layoutPlayers.addView(button)
            }
        }


        showRole.setOnClickListener({ view ->
            hidden.visibility = View.VISIBLE
        })
        fab.setOnClickListener { view ->
            // Si il n'y plus de joueur, la journé arrive
            if(player == GameEngine.GetPlayers().last()){
                GameEngine.ResetTurn()
                val intent = Intent(this, DeadActivity::class.java)
                startActivity(intent)
            }else{
                GameEngine.NextPlayer()
                val intent = Intent(this, NightActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
