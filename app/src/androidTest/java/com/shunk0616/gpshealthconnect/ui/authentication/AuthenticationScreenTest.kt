package com.shunk0616.gpshealthconnect.ui.authentication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AuthenticationScreenTest {

    private val errorMessageText = "エラーが発生しました"

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun authenticationScreen_displaysSignInButton() {
        // Given
        val onSignInClick: () -> Unit = {}
        val onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> true }
        val errorMessage: String? = null
        
        // When
        composeTestRule.setContent {
            AuthenticationScreen(
                onSignInClick = onSignInClick,
                onShowSnackbar = onShowSnackbar,
                errorMessage = errorMessage
            )
        }
        
        // Then
        composeTestRule.onNodeWithText("このスマホで始める").assertIsDisplayed()
    }

    @Test
    fun signInButton_clickTriggersLoginAndNavigation() {
        // Given
        var signInClicked = false
        val onSignInClick: () -> Unit = { signInClicked = true }
        val onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> true }
        val errorMessage: String? = null
        
        // When
        composeTestRule.setContent {
            AuthenticationScreen(
                onSignInClick = onSignInClick,
                onShowSnackbar = onShowSnackbar,
                errorMessage = errorMessage
            )
        }
        
        // Click the sign in button
        composeTestRule.onNodeWithText("このスマホで始める").performClick()
        
        // Then
        assert(signInClicked) { "Sign in button click was not handled" }
    }

    @Test
    fun authenticationScreen_displaysErrorMessage() {
        // Given
        val onSignInClick: () -> Unit = {}
        val onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> true }
        val errorMessage: String = errorMessageText
        
        // When
        composeTestRule.setContent {
            AuthenticationScreen(
                onSignInClick = onSignInClick,
                onShowSnackbar = onShowSnackbar,
                errorMessage = errorMessage
            )
        }
        
        // Then
        composeTestRule.onNodeWithText(errorMessageText).assertIsDisplayed()
    }

    @Test
    fun onShowSnackbar_isCalledWhenErrorOccurs() {
        // Given
        var snackbarShown = false
        val onSignInClick: () -> Unit = {}
        val onShowSnackbar: suspend (String, String?) -> Boolean = { message, _ ->
            snackbarShown = message == errorMessageText
            true
        }
        
        // When
        composeTestRule.setContent {
            AuthenticationScreen(
                onSignInClick = onSignInClick,
                onShowSnackbar = onShowSnackbar,
                errorMessage = errorMessageText
            )
        }
        
        // Then
        assert(snackbarShown) { "Snackbar was not shown with error message" }
    }
}
