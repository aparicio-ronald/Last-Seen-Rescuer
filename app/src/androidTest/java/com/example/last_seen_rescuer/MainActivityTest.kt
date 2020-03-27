package com.example.last_seen_rescuer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val mainActivityTestRule : ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private val firstNameInput = onView(withId(R.id.firstName))
    private val lastNameInput = onView(withId(R.id.lastName))
    private val submitButton = onView(withId(R.id.button))

    @Before
    fun initIntent() {
        Intents.init()
    }

    @After
    fun releaseIntent() {
        Intents.release()
    }

    @Test
    fun submitTest() {
        submitButton.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(isDisplayed()))

        lastNameInput.perform(click())
        lastNameInput.perform(typeText("Test"))
        lastNameInput.perform(closeSoftKeyboard())

        submitButton.perform(click())
        onView(withText("REQUIRED FIELDS CANNOT BE EMPTY")).inRoot(ToastMatcher()).check(matches(isDisplayed()))

        firstNameInput.perform(click())
        firstNameInput.perform(typeText("Test"))
        firstNameInput.perform(closeSoftKeyboard())

        submitButton.perform(click())
        intended(hasComponent(SelectionPage::class.java.name))
    }
}