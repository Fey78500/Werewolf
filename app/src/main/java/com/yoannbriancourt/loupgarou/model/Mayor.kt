package com.yoannbriancourt.loupgarou.model

class Mayor(id:Int,name : String, health: Int, nbrVote:Int, isBad:Boolean) : Villager(id,name,health,nbrVote,isBad,nothingAtNight = true,killPotion = 0,savePotion = 0) {
    override fun description() : String{
        return "The Mayor count double on the vote"
    }
}