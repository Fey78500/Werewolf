package com.yoannbriancourt.loupgarou

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var playersName : ArrayList<String> = ArrayList()
    private var nbrPlayers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if(GameEngine.getPlayersName().size != 0){
            setOldPlayers()
        }

        setListener()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setListener(){
        add.setOnClickListener {
            addInput()
        }
        remove.setOnClickListener {
            removeInput()
        }
        fab.setOnClickListener {
            giveRoles()
        }
    }

    private fun addInput(){
        val text = EditText(this)
        text.hint = "Player name"
        text.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutPlayersName.addView(text)
    }

    private fun removeInput(){
        layoutPlayersName.removeAllViews()
    }

    private fun getPlayerName(){
        for(index in layoutPlayersName.childCount - 1 downTo 0){
            val child = layoutPlayersName.getChildAt(index) as EditText
            if(child.text.toString() == "") {
                Toast.makeText(this, getString(R.string.checkPlayerName), Toast.LENGTH_SHORT).show()
                return
            }
            this.playersName.add(child.text.toString())
            this.nbrPlayers ++
        }
    }

    private fun setOldPlayers(){
        for(playerName in GameEngine.getPlayersName()){
            val text = EditText(this)
            text.hint = "Player name"
            text.setText(playerName)
            text.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutPlayersName.addView(text)
        }
    }

    private fun giveRoles(){
        getPlayerName()
        if(checkPlayers()){
            GameEngine.createRoles(this.nbrPlayers - 1,this.playersName)
            intentToNight()
        }
    }

    private fun checkPlayers() : Boolean{
        if(this.nbrPlayers > 2) {
            return true
        }
        Toast.makeText(this, getString(R.string.checkPlayerNumber), Toast.LENGTH_SHORT).show()
        return false
    }

    private fun intentToNight(){
        val intent = Intent(this, NightActivity::class.java)
        startActivity(intent)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.clickBack), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
