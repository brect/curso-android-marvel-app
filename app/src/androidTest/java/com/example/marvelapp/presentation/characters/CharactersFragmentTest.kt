package com.example.marvelapp.presentation.characters

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marvelapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CharactersFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        launchFragmentInHiltContainer<CharactersFragment>()
    }

    @Test
    fun shouldShowCharacters_when_ViewIsCreated(){

    }

}
