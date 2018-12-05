package com.yoannbriancourt.loupgarou.model

class Seer(id:Int,name : String, health: Int,nbrVote:Int,isBad:Boolean) : Villager(id,name,health,nbrVote,isBad,nothingAtNight=false,killPotion = 0,savePotion = 0) {
    override fun description() : String{
        return "The Seer can select someone and you will see his role"
    }
}