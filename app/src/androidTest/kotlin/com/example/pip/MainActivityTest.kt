package com.example.pip

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertExists
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainActivity_displaysText() {
        composeTestRule.setContent {
            MainActivity().onCreate(null)
        }
        composeTestRule.onNodeWithText("Pip").assertExists()
    }
}