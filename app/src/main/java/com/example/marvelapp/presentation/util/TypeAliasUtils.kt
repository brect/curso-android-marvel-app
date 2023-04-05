package com.example.marvelapp.presentation

import android.view.View
import com.example.core.domain.model.Character

typealias OnCharacterItemClick =  (character: Character, view: View) -> Unit