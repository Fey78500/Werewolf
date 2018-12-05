package com.yoannbriancourt.loupgarou.model

open class Villager(var id: Int,var name: String, var health: Int, var nbrVote: Int, var isBad: Boolean,var nothingAtNight : Boolean = true,var savePotion : Int, var killPotion : Int) {
    var actions : HashMap<Villager,String> = HashMap()


    fun addAction(player : Villager,action : String){
        this.actions[player] = action
    }

    fun die(){
        this.actions = HashMap()
        this.health = 0
        this.nbrVote = 0
    }

    fun addVote(nbr : Int){
        this.nbrVote += nbr
    }

    open fun description() : String{
        return "The villager can't do anything. Sleep well"
    }
}