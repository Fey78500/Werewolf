package com.yoannbriancourt.loupgarou.model

class Hunter(id:Int,name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(id,name,health,nbrVote,isBad,nothingAtNight = false,killPotion = 0,savePotion = 0)  {
    override fun description() : String{
        return "The hunter can target someone, if you die, the target die too"
    }
}