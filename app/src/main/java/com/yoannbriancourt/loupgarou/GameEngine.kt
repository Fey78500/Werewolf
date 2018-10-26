package com.yoannbriancourt.loupgarou

import com.yoannbriancourt.loupgarou.model.*
import java.util.*

object GameEngine {
    private var players : ArrayList<Villager> = ArrayList()
    private var alivePlayer : Int = 0
    private var turn : Int = 0

    private fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    fun getPlayers(): ArrayList<Villager>{
        return this.players
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
        if(playerClass == "Werewolf" || playerClass == "Sorcerer"){
            actions.add("kill")
        }
        if(playerClass == "Sorcerer"){
            actions.add("save")
        }
        if(playerClass == "Hunter"){
            actions.add("target")
        }
        if(playerClass == "Seer"){
            actions.add("see")
        }

        return actions
    }
}