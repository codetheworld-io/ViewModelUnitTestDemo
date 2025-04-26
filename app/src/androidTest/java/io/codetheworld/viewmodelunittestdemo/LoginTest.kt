package io.codetheworld.viewmodelunittestdemo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import io.codetheworld.viewmodelunittestdemo.EspressoExtensions.Companion.waitForViewVisible
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TIMEOUT = 20000L

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun shouldHandleWrongUsernameOrPassword() {
        val errorMessage = "Username or password is not correct!"
        onView(withHint("Username")).perform(
            click(),
            typeText("wrong value"),
        )
        onView(withHint("Password")).perform(
            click(),
            typeText("admin"),
        )
        onView(withText("Login")).perform(click())
        device.wait(Until.hasObject(By.text(errorMessage)), TIMEOUT)
        onView(withText(errorMessage)).check(matches(isDisplayed()))

        onView(withHint("Username")).perform(
            click(),
            clearText(),
            typeText("admin"),
        )
        onView(withHint("Password")).perform(
            click(),
            clearText(),
            typeText("wrong value"),
        )
        onView(withText("Login")).perform(click())
        device.wait(Until.hasObject(By.text(errorMessage)), TIMEOUT)
        waitForViewVisible(withText(errorMessage)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun shouldLoginWithUsernameAndPassword() {
        onView(withHint("Username")).perform(
            click(),
            typeText("admin"),
        )
        onView(withHint("Password")).perform(
            click(),
            typeText("admin"),
        )
        onView(withText("Login")).perform(click())

        device.wait(Until.hasObject(By.text("Hello World!")), TIMEOUT)
        onView(withText("Hello World!")).check(matches(isDisplayed()))
    }
}