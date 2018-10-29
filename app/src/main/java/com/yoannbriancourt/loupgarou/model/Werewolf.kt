package com.yoannbriancourt.loupgarou.model

class Werewolf(name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(name,health,nbrVote,isBad = true) {
    override fun description() : String{
        return "You are the Werewolf, let's eat the village !"
    }
}