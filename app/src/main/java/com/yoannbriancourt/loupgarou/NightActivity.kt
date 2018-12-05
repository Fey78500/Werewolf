package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.yoannbriancourt.loupgarou.model.Villager
import kotlinx.android.synthetic.main.activity_night.*
import kotlinx.android.synthetic.main.content_night.*

class NightActivity : AppCompatActivity() {
    private val player = GameEngine.getPlayer()
    private lateinit var players : ArrayList<Villager>
    private lateinit var playerAction : String
    private lateinit var choosenPlayer : Villager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night)
        setSupportActionBar(toolbar)
        this.players = GameEngine.getOtherPlayers(player)
        //set les textes en fonction du player
        setText()
        if(!player.nothingAtNight){
            // Affiche les actions possible en fonction du role
            getActions()
            // Affiche les joueurs vivants
            getPlayers()
        }else{
            chooseAction.visibility = View.INVISIBLE
            choosePlayer.visibility = View.INVISIBLE
        }
        // Affiche le role lors du click
        showRole.setOnClickListener{
            hidden.visibility = View.VISIBLE
        }
        //Continue vers le prochain joueur, ou le matin
        fab.setOnClickListener {
            // Si il n'y plus de joueur, la journé arrive
            onNextTap()
        }
    }

    private fun setText(){
        username.text = player.name
        role.text = player.javaClass.simpleName
        description.text = player.description()
    }

    private fun getActions(){
        for(action in GameEngine.getActions(player)){
            if(player.javaClass.simpleName == "Sorcerer"){
                if(action == "kill" && player.killPotion == 0){
                    continue
                }
                if(action == "save" && player.savePotion == 0){
                    continue
                }
            }
            val button = Button(this)
            button.text = action
            button.background = getDrawable(R.drawable.button_custom)
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutAction.addView(button)
            if(GameEngine.getActions(player).size == 1){
                button.isActivated = true
                playerAction = action
            }
            button.setOnClickListener {
                for (i in 0 until layoutAction.childCount) {
                    val child = layoutAction.getChildAt(i)
                    child.isActivated = false
                }
                it.isActivated = true
                playerAction = action
            }
        }
    }

    private fun getPlayers(){
        for(otherPlayer in players) {
            val button = Button(this)
            if(otherPlayer.javaClass.simpleName == "Werewolf" && this.player.javaClass.simpleName == "Werewolf"){
                button.text = getString(R.string.buttonPlayerWithRole,otherPlayer.name,otherPlayer.javaClass.simpleName)
                button.isClickable = false
            }else{
                button.text = otherPlayer.name
            }
            button.background = getDrawable(R.drawable.button_custom)
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutPlayers.addView(button)

            button.setOnClickListener {
                for (i in 0 until layoutPlayers.childCount) {
                    val child = layoutPlayers.getChildAt(i)
                    child.isActivated = false
                    if(player.javaClass.simpleName == "Seer"){
                        child.isClickable = false
                    }
                }
                it.isActivated = true
                if(player.javaClass.simpleName == "Seer") {
                    button.text = getString(R.string.buttonPlayerWithRole,otherPlayer.name,otherPlayer.javaClass.simpleName)
                }
                choosenPlayer = otherPlayer
            }
        }
    }

    private fun onNextTap(){

        if(!player.nothingAtNight){
            if(::playerAction.isInitialized && ::choosenPlayer.isInitialized) {
                GameEngine.setAction(playerAction, choosenPlayer, player)
                this.changeView()
            }else if(::playerAction.isInitialized && playerAction == "nothing"){
                this.changeView()
            }else{
                Toast.makeText(this, getString(R.string.checkActionAndPlayer), Toast.LENGTH_SHORT).show()
            }
        }else{
            this.changeView()
        }

    }

    private fun changeView(){
        if(player == GameEngine.getPlayers().last()){
            GameEngine.resetTurn()
            val intent = Intent(this, DeadActivity::class.java)
            startActivity(intent)
        }else {
            GameEngine.nextPlayer()
            val intent = Intent(this, NightActivity::class.java)
            startActivity(intent)
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
