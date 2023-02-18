package com.example.testing.model

import com.example.core.domain.model.Character

class CharactorFactory {

    fun create(hero: Hero) = when (hero){
        Hero.ThreeDMan ->  Character("Hero 1", "https:sitemarvel.com/1.jpg")
        Hero.ABom ->  Character("Hero 2", "https:sitemarvel.com/2.jpg")
    }

    sealed class Hero {
        object ThreeDMan : Hero()
        object ABom : Hero()
    }
}