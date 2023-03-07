package com.example.core.marvelapp.factory.response

import com.example.core.domain.model.CharacterPaging
import com.example.core.domain.model.Character

class CharacterPagingFactory {

    fun create() = CharacterPaging(
        offset = 0,
        total = 2,
        characters = listOf(
            Character(
                1, "Hero 1", "https:sitemarvel.com/1.jpg"
            ),
            Character(
                2, "Hero 2", "https:sitemarvel.com/2.jpg"
            )
        )
    )


}