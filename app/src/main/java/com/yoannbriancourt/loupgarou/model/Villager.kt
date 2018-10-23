package com.yoannbriancourt.loupgarou.model

open class Villager {
    private var name : String
    private var health : Int

    constructor(name:String,health:Int){
        this.name = name
        this.health = health
    }
}