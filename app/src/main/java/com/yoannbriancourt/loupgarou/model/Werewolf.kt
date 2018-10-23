package com.yoannbriancourt.loupgarou.model

class Werewolf(name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(name,health,nbrVote,isBad = true) {

    fun killPlayer(player : Villager){
        player.health--
    }
}