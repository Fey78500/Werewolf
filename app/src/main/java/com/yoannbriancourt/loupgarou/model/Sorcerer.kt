package com.yoannbriancourt.loupgarou.model

class Sorcerer(id: Int, name: String, health: Int, nbrVote: Int, isBad: Boolean, savePotion: Int, killPotion: Int) : Villager(id,name,health,nbrVote,isBad,nothingAtNight = false,savePotion = 1,killPotion = 1) {

    override fun description() : String{
        if(this.savePotion == 0 && killPotion == 0){
            return "The Sorcerer can save or kill someone, but you have used all of your potions. To bad !"
        }
        return "The Sorcerer can save or kill someone"
    }
}