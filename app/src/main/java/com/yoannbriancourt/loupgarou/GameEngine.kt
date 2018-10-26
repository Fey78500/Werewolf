package com.yoannbriancourt.loupgarou

import com.yoannbriancourt.loupgarou.model.*
import java.util.*

object GameEngine {
    private var players : ArrayList<Villager> = ArrayList()
    private var alivePlayer : Int = 0
    private var turn : Int = 0

    fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    fun GetPlayers(): ArrayList<Villager>{
        return this.players
    }
    fun GetPlayer(index : Int) : Villager{
        return players[index]
    }

    fun NextPlayer(){
        this.turn++
    }

    fun ResetTurn(){
        this.turn = 0
    }

    fun GetTurn() : Int{
        return this.turn
    }

    fun CreateRoles(nbr : Int){
        this.alivePlayer = nbr
        var player : Villager
        var isThereBad = false
        while(!isThereBad){
            players = ArrayList()
            for(i in nbr downTo 0){
                var random = (0..4).random()
                when(random){
                    0 ->  player = Werewolf("player $i",1,0,true)
                    1 ->  player = Hunter("player $i",1,0,false)
                    2 ->  player = Seer("player $i",1,0,false)
                    3 ->  player = Sorcerer("player $i",1,0,false)
                    4 ->  player = Villager("player $i",1,0,false)
                    else -> {
                        player = Villager("player $i",1,0,false)
                    }
                }
                if(player.isBad == true){
                    isThereBad = true
                }
                this.players.add(player)
            }
        }

    }
}