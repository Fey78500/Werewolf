package com.yoannbriancourt.loupgarou.model

class Sorcerer(name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(name,health,nbrVote,isBad) {
    override fun description() : String{
        return "The Sorcerer can save or kill someone"
    }
}