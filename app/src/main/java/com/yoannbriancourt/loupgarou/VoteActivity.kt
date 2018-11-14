package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.yoannbriancourt.loupgarou.model.Villager
import kotlinx.android.synthetic.main.activity_vote.*
import kotlinx.android.synthetic.main.content_night.*

class VoteActivity : AppCompatActivity() {

    private var turn = GameEngine.getTurn()
    private val player = GameEngine.getPlayer(turn)
    private lateinit var players : ArrayList<Villager>
    private lateinit var choosenPlayer : Villager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        setSupportActionBar(toolbar)

        username.text = player.name

        this.players = GameEngine.getOtherPlayers(player)
        // Affiche les joueurs vivants
        getPlayers()

        fab.setOnClickListener { view ->
            // Si il n'y plus de joueur, le vote se termine arrive
            onNextTap()
        }
    }

    private fun getPlayers(){
        for(otherPlayer in players) {
            val button = Button(this)
            button.text = otherPlayer.name
            button.background = getDrawable(R.drawable.button_custom)
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutPlayers.addView(button)
            button.setOnClickListener {
                for (i in 0 until layoutPlayers.childCount) {
                    val child = layoutPlayers.getChildAt(i)
                    child.isActivated = false
                }
                it.isActivated = true
                choosenPlayer = otherPlayer
            }
        }
    }

    private fun onNextTap(){

        if(::choosenPlayer.isInitialized) {
            GameEngine.setVote(choosenPlayer, player)
            changeView()
        }else{
            Toast.makeText(this, "Please choose a player", Toast.LENGTH_SHORT).show()
        }

    }

    private fun changeView(){
        if(player == GameEngine.getPlayers().last()){
            GameEngine.resetTurn()
            val intent = Intent(this, DeadActivity::class.java)
            startActivity(intent)
        }else {
            GameEngine.nextPlayer()
            val intent = Intent(this, VoteActivity::class.java)
            startActivity(intent)
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

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}
