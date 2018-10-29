package com.yoannbriancourt.loupgarou

import com.yoannbriancourt.loupgarou.model.*
import java.util.*
import kotlin.collections.ArrayList

object GameEngine {
    private var players : ArrayList<Villager> = ArrayList()
    private var alivePlayer : Int = 0
    private var turn : Int = 0

    private fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    fun getPlayers(player : Villager): List<Villager>{
        //Get all player except the courrant one
        return players.filterNot {
            it == player
        }
    }

    fun getPlayer(index : Int) : Villager{
        return players[index]
    }

    fun nextPlayer(){
        this.turn++
    }

    fun resetTurn(){
        this.turn = 0
    }

    fun getTurn() : Int{
        return this.turn
    }

    fun createRoles(nbr : Int){
        this.alivePlayer = nbr
        var player : Villager
        var isThereBad = false
        while(!isThereBad){
            players = ArrayList()
            for(i in nbr downTo 0){
                val random = (0..4).random()
                player = when(random){
                    0 -> Werewolf("player $i",1,0,true)
                    1 -> Hunter("player $i",1,0,false)
                    2 -> Seer("player $i",1,0,false)
                    3 -> Sorcerer("player $i",1,0,false)
                    4 -> Villager("player $i",1,0,false)
                    else -> {
                        Villager("player $i",1,0,false)
                    }
                }
                if(player.isBad){
                    isThereBad = true
                }
                this.players.add(player)
            }
        }

    }

    fun getActions(player : Villager) : ArrayList<String>{
        val playerClass = player.javaClass.simpleName
        val actions = ArrayList<String>()
        when(playerClass){
            "Werewolf" -> actions.add("kill")
            "Sorcerer" -> {
                actions.add("save")
                actions.add("kill")
            }
            "Hunter" -> actions.add("target")
            "Seer" -> actions.add("see")
            else -> {
                actions.add("nothing")
            }
        }
        return actions
    }

    fun setAction(action:String, targetPlayer : Villager,currentPlayer : Villager){
        targetPlayer.addAction(currentPlayer,action)
    }

    fun getDeadPlayer() : ArrayList<Villager>{
        val deadPlayers : ArrayList<Villager> = ArrayList()
        for(targetPlayer in players){
            for(actionPlayer in players){
                if(targetPlayer.actions[actionPlayer] == "kill"){
                    targetPlayer.health --
                }
                if(targetPlayer.actions[actionPlayer] == "save"){
                    if(targetPlayer.health == 0) {
                        targetPlayer.health++
                    }
                    break
                }
            }
            if(targetPlayer.health == 0){
                deadPlayers.add(targetPlayer)
            }
        }
        val searchHunter = deadPlayers.find { villager ->
            villager.javaClass.simpleName == "Hunter"
        }
        if(searchHunter != null){
            for(targetPlayer in players) {
                if(targetPlayer.actions[searchHunter] == "target"){
                    targetPlayer.health --
                    deadPlayers.add(targetPlayer)
                }
            }
        }
        players.removeAll(deadPlayers)
        return deadPlayers
    }

    fun checkWinning(){

    }
}