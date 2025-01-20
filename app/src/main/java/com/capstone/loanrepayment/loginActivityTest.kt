package com.capstone.loanrepayment

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capstone.loanrepayment.services.AuthService
import com.capstone.loanrepayment.util.TokenManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.*

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var context: Context
    private lateinit var activityScenario: ActivityScenario<LoginActivity>
    private lateinit var mockAuthService: AuthService
    private lateinit var mockTokenManager: TokenManager

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        mockAuthService = Mockito.mock(AuthService::class.java)
        mockTokenManager = Mockito.mock(TokenManager::class.java)
        activityScenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun testLoginSuccess() {
        val username = "testuser"
        val password = "password"
        val token = "testtoken"

        // Mock the authenticateUser method to simulate successful login
        doAnswer {
            val callback = it.getArgument<(Boolean, String?, String?) -> Unit>(2)
            callback(true, token, null)
        }.whenever(mockAuthService).authenticateUser(eq(username), eq(password), any())

        // Simulate button click
        activityScenario.onActivity { activity ->
            activity.binding.usernameEditText.setText(username)
            activity.binding.passwordEditText.setText(password)
            activity.binding.loginButton.performClick()
        }

        // Verify the token is saved and MainActivity is started
        activityScenario.onActivity { activity ->
            verify(mockTokenManager).saveToken(eq(activity), eq(token))
            val expectedIntent = Intent(activity, MainActivity::class.java)
            val actualIntent = ShadowActivity.getShadowIntent(activity.intent)
            assertEquals(expectedIntent.component, actualIntent.component)
        }
    }

    @Test
    fun testLoginFailure() {
        val username = "testuser"
        val password = "password"
        val errorMessage = "Invalid credentials"

        // Mock the authenticateUser method to simulate login failure
        doAnswer {
            val callback = it.getArgument<(Boolean, String?, String?) -> Unit>(2)
            callback(false, null, errorMessage)
        }.whenever(mockAuthService).authenticateUser(eq(username), eq(password), any())

        // Simulate button click
        activityScenario.onActivity { activity ->
            activity.binding.usernameEditText.setText(username)
            activity.binding.passwordEditText.setText(password)
            activity.binding.loginButton.performClick()
        }

        // Verify the error message is shown
        activityScenario.onActivity { activity ->
            verify(mockTokenManager, never()).saveToken(any(), any())
            assertEquals(Toast.LENGTH_SHORT, ShadowToast.getTextOfLatestToast(context).length)
            assertEquals(errorMessage, ShadowToast.getTextOfLatestToast(context))
        }
    }
}
