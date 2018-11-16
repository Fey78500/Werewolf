package com.yoannbriancourt.loupgarou

import com.yoannbriancourt.loupgarou.model.*
import java.util.*
import kotlin.collections.ArrayList

object GameEngine {
    private var players : ArrayList<Villager> = ArrayList()
    private var playersName : ArrayList<String> = ArrayList()
    private var turn : Int = 0
    private var day : Boolean = true
    private fun selectorNbrVote(p: Villager): Int = p.nbrVote
    private fun selectorId(p: Villager): Int = p.id


    fun getPlayersName(): ArrayList<String>{
        return this.playersName
    }

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
        return ArrayList(ArrayList(this.players.filterNot {it == player }).filterNot{it.health < 1})
    }

    fun getAllPlayers() : ArrayList<Villager>{
        return this.players
    }

    fun getPlayers() : ArrayList<Villager>{
        return ArrayList(this.players.filterNot {it.health < 1 })
    }

    fun getPlayer() : Villager{
        while(this.players[this.turn].health < 1){
            this.turn++
        }
        return this.players[this.turn]
    }

    fun nextPlayer(){
        this.turn++
    }

    fun resetTurn(){
        this.turn = 0
    }

    fun createRoles(nbr : Int, playersName : ArrayList<String>){
        var isThereBad = false
        while(!isThereBad){
            this.players = ArrayList()
            for(i in nbr downTo 0){
                val random = (0..5).random()
                val name = playersName[i]
                val player = when(random){
                    0 -> Werewolf(i,name,1,0)
                    1 -> Hunter(i,name,1,0,false)
                    2 -> Seer(i,name,1,0,false)
                    3 -> Sorcerer(i,name,1,0,false)
                    4 -> Mayor(i,name,1,0,false)
                    5 -> Villager(i,name,1,0,false,true)
                    else -> {
                        Villager(i,name,1,0,false,true)
                    }
                }
                if(player.isBad){
                    isThereBad = true
                }
                this.players.add(player)
            }
        }
        this.playersName = playersName
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
        if(this.day){
            for(targetPlayer in players){
                for(actionPlayer in players){
                    if(targetPlayer.actions[actionPlayer] == "kill"){
                        targetPlayer.die()
                    }
                    if(targetPlayer.actions[actionPlayer] == "save"){
                        if(targetPlayer.health < 1) {
                            targetPlayer.health = 1
                        }
                        break
                    }
                }
                if(targetPlayer.health < 1){
                    deadPlayers.add(targetPlayer)
                }
            }
            val searchHunter = deadPlayers.find { villager ->
                villager.javaClass.simpleName == "Hunter"
            }
            if(searchHunter != null){
                for(targetPlayer in this.players) {
                    if(targetPlayer.actions[searchHunter] == "target"){
                        targetPlayer.die()
                        if(targetPlayer.health < 1){
                            deadPlayers.add(targetPlayer)
                        }
                    }
                }
            }
        }else{
            this.players.sortBy { selectorNbrVote(it) }
            if(this.players[0].nbrVote != this.players[1].nbrVote){
                this.players[0].die()
                deadPlayers.add(this.players[0])
            }
            this.players.sortByDescending { selectorId(it) }
        }
        return deadPlayers
    }

    fun checkWinning() : String{
        var isThereBad = false
        var isThereGood = false
        for(player in this.players){
            if(player.isBad && player.health > 0) {
                isThereBad = true
            }else if(!player.isBad && player.health > 0){
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
        resetTurn()
        this.day = true
    }

}