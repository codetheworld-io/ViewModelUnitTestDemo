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
import io.codetheworld.viewmodelunittestdemo.EspressoExtensions.Companion.waitForViewVisible
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun shouldHandleWrongUsernameOrPassword() {
        onView(withHint("Username")).perform(
            click(),
            typeText("wrong value"),
        )
        onView(withHint("Password")).perform(
            click(),
            typeText("admin"),
        )
        onView(withText("Login")).perform(click())
        waitForViewVisible(withText("Username or password is not correct!")).check(matches(isDisplayed()))

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
        waitForViewVisible(withText("Username or password is not correct!")).check(matches(isDisplayed()))
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

        waitForViewVisible(withText("Hello World!")).check(matches(isDisplayed()))
    }
}