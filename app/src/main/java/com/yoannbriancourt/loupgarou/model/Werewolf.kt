package com.yoannbriancourt.loupgarou.model

class Werewolf(id:Int,name : String, health: Int,nbrVote:Int) : Villager(id,name,health,nbrVote,isBad = true,nothingAtNight = false,killPotion = 0,savePotion = 0) {
    override fun description() : String{
        return "You are the Werewolf, let's eat the village !"
    }
}