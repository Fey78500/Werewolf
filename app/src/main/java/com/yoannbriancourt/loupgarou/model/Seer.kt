package com.yoannbriancourt.loupgarou.model

class Seer(name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(name,health,nbrVote,isBad) {
    override fun description() : String{
        return "The Seer can select someone and you will see his role"
    }
}