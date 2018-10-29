package com.yoannbriancourt.loupgarou.model

class Hunter(name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(name,health,nbrVote,isBad)  {
    override fun description() : String{
        return "The hunter can target someone, if you die, the target die too"
    }
}