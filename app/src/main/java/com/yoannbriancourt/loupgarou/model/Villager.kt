package com.yoannbriancourt.loupgarou.model

open class Villager {
    var name : String
    var health : Int
    var nbrVote: Int = 0
    var isBad : Boolean = false

    constructor(name:String,health:Int,nbrVote:Int,isBad:Boolean){
        this.name = name
        this.health = health
        this.nbrVote = nbrVote
        this.isBad = isBad
    }

    fun votePlayer(player : Villager){
        player.nbrVote ++
    }
}