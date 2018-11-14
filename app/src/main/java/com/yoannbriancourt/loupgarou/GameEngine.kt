package com.yoannbriancourt.loupgarou

import com.yoannbriancourt.loupgarou.model.*
import java.util.*
import kotlin.collections.ArrayList

object GameEngine {
    private var players : ArrayList<Villager> = ArrayList()
    private var allPlayers : ArrayList<Villager> = ArrayList()
    private var turn : Int = 0
    private var day : Boolean = true
    private lateinit var  maxVote : Villager
    private fun selector(p: Villager): Int = p.nbrVote

    fun setDay(day:Boolean){
        this.day = day
    }

    fun getDay():Boolean{
        return this.day
    }

    private fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) +  start

    fun getOtherPlayers(player : Villager): ArrayList<Villager>{
        //Get all player except the courrant one
        return ArrayList(this.players.filterNot {
            it == player
        })
    }

    fun getAllPlayers() : ArrayList<Villager>{
        return this.allPlayers
    }

    fun getPlayers() : ArrayList<Villager>{
        return this.players
    }

    fun getPlayer(index : Int) : Villager{
        return this.players[index]
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

    fun createRoles(nbr : Int, playersName : ArrayList<String>){
        var isThereBad = false
        while(!isThereBad){
            this.players = ArrayList()
            this.allPlayers = ArrayList()
            for(i in nbr downTo 0){
                val random = (0..4).random()
                val name = "$i: ${playersName[i]}"
                val player = when(random){
                    0 -> Werewolf(name,1,0,true)
                    1 -> Hunter(name,1,0,false)
                    2 -> Seer(name,1,0,false)
                    3 -> Sorcerer(name,1,0,false)
                    4 -> Villager(name,1,0,false)
                    else -> {
                        Villager(name,1,0,false)
                    }
                }
                if(player.isBad){
                    isThereBad = true
                }
                this.players.add(player)
                this.allPlayers.add(player)
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

    fun setVote(targetPlayer : Villager,currentPlayer : Villager){
        if(currentPlayer.javaClass.simpleName != "Mayor"){
            targetPlayer.addVote(1)
        }else{
            targetPlayer.addVote(2)
        }
    }

    fun getDeadPlayer() : ArrayList<Villager>{
        val deadPlayers : ArrayList<Villager> = ArrayList()
        var draw = false
        if(this.day){
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
                for(targetPlayer in this.players) {
                    if(targetPlayer.actions[searchHunter] == "target"){
                        targetPlayer.health --
                        if(targetPlayer.health == 0){
                            deadPlayers.add(targetPlayer)
                        }
                    }
                }
            }
        }else{
            this.players.sortBy {selector(it)}
            for(votePlayer in this.players){
                when {
                    votePlayer == this.players.first() -> this.maxVote = votePlayer
                    this.maxVote.nbrVote == votePlayer.nbrVote -> draw = true
                    this.maxVote.nbrVote < votePlayer.nbrVote -> this.maxVote = votePlayer
                }
            }
            if(!draw) {
                deadPlayers.add(maxVote)
            }
        }
        if(!draw){
            this.players.removeAll(deadPlayers)
        }
        return deadPlayers
    }

    fun checkWinning() : String{
        var isThereBad = false
        var isThereGood = false
        for(player in this.players){
            if(player.isBad) {
                isThereBad = true
            }else if(!player.isBad){
                isThereGood = true
            }
        }
        if(!isThereBad && !isThereGood) {
            return "Draw"
        }else if(!isThereBad && isThereGood){
            return "Villager"
        }else if(isThereBad && !isThereGood){
            return "Werewolf"
        }
        return "continue"
    }

    fun restart(){
        this.players = ArrayList()
        this.allPlayers = ArrayList()
        resetTurn()
        this.day = true
    }

}