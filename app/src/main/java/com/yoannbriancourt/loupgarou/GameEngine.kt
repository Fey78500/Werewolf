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

    private fun resetVote(){
        for(player in players){
            player.nbrVote = 0
        }
    }
    fun createRoles(nbr : Int, playersName : ArrayList<String>){
        var nbrBad = 0
        while(nbrBad == 0 || nbrBad > this.players.count()/2){
            nbrBad = 0
            this.players = ArrayList()
            var isThereMayor = false
            for(i in nbr downTo 0){
                val player = this.getRandomRole(isThereMayor,i,playersName[i])
                nbrBad += checkIsBad(player.isBad)
                isThereMayor = checkMayor(player.javaClass.simpleName)
                this.players.add(player)
            }
        }
        this.playersName = playersName
    }
    private fun checkIsBad(isBad : Boolean) : Int{
        if(isBad){
            return 1
        }
        return 0
    }

    private fun checkMayor(className : String) : Boolean{
        if(className == "Mayor"){
            return true
        }
        return false
    }

    private fun getRandomRole(isThereMayor : Boolean,i : Int,name : String) : Villager{
        var random = (0..5).random()
        if(isThereMayor){
            random = (0..4).random()
        }
        return when(random){
            0 -> Werewolf(i,name,1,0)
            1 -> Hunter(i,name,1,0,false)
            2 -> Seer(i,name,1,0,false)
            3 -> Sorcerer(i, name, 1, 0, false, 1,1)
            4 -> Villager(i,name,1,0,false,true,0,0)
            5 -> Mayor(i,name,1,0,false)
            else -> {
                Villager(i,name,1,0,false,true,0,0)
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
                actions.add("nothing")
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
            deadPlayers.addAll(this.nightCycle(deadPlayers))
        }else{
            deadPlayers.addAll(this.dayCycle(deadPlayers))
        }
        return deadPlayers
    }

    private fun dayCycle(deadPlayers : ArrayList<Villager>): ArrayList<Villager>{
        this.players.sortByDescending { selectorNbrVote(it) }
        if(this.players[0].nbrVote != this.players[1].nbrVote){
            this.players[0].die()
            deadPlayers.add(this.players[0])
        }
        this.resetVote()
        this.players.sortByDescending { selectorId(it) }
        return deadPlayers
    }

    private fun nightCycle(deadPlayers : ArrayList<Villager>) : ArrayList<Villager>{
        for(targetPlayer in this.getPlayers()){
            for(actionPlayer in this.getPlayers()){
                //Pour chaque joueur l'action de tuer se fait
                if(targetPlayer.actions[actionPlayer] == "kill"){
                    if(actionPlayer.javaClass.simpleName == "Sorcerer"){
                        actionPlayer.killPotion = 0
                        if(actionPlayer.savePotion == 0 && actionPlayer.killPotion == 0){
                            actionPlayer.nothingAtNight = true
                        }
                    }
                    targetPlayer.health --
                }
                // Si un sorcier soigne quelqu'un, il ne meurt pas
                if(targetPlayer.actions[actionPlayer] == "save"){
                    actionPlayer.savePotion = 0
                    if(targetPlayer.health < 1) {
                        targetPlayer.health = 1
                    }
                    if(actionPlayer.savePotion == 0 && actionPlayer.killPotion == 0){
                        actionPlayer.nothingAtNight = true
                    }
                    break
                }
            }
            // Les joueurs avec moins de 1 en vie meurt
            if(targetPlayer.health < 1){
                // Si il sont chasseur il tire leur balle avant de mourrire
                if(targetPlayer.javaClass.simpleName == "Hunter"){
                    for(hunterTarget in this.players) {
                        if(hunterTarget.actions[targetPlayer] == "target"){
                            hunterTarget.health --
                            if(hunterTarget.health < 1){
                                hunterTarget.die()
                                deadPlayers.add(hunterTarget)
                            }
                        }
                    }
                }
                targetPlayer.die()
                deadPlayers.add(targetPlayer)
            }
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
        return when {
            !isThereBad && !isThereGood -> "Draw"
            !isThereBad && isThereGood -> "Villager"
            isThereBad && !isThereGood -> "Werewolf"
            else -> "continue"
        }
    }

    fun restart(){
        this.players = ArrayList()
        this.resetTurn()
        this.day = true
    }

}