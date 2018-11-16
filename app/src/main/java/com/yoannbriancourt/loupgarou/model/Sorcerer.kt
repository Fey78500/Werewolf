package com.yoannbriancourt.loupgarou.model

class Sorcerer(id:Int,name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(id,name,health,nbrVote,isBad,nothingAtNight = false) {
    override fun description() : String{
        return "The Sorcerer can save or kill someone"
    }
}