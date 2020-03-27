package com.example.last_seen_rescuer

import android.content.Intent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SelectionPageTest {
    @Rule
    @JvmField
    val selectionPageTestRule = ActivityTestRule(SelectionPage::class.java, false, false)

    private val addProfileButton = onView(withId(R.id.temporary_add_profile_button))

    @Before
    fun initIntentAndBundle() {
        Intents.init()

        val intent = Intent()

        intent.putExtra("firstName", "TEST")
        intent.putExtra("lastName", "TEST")
        intent.putExtra("dateOfBirth", "01/01/1900")
        intent.putExtra("streetAddress", "TEST STREET")
        intent.putExtra("city", "TEST")
        intent.putExtra("state", "TT")
        intent.putExtra("zipCode", "11111")

        selectionPageTestRule.launchActivity(intent)
    }

    @After
    fun releaseIntent() {
        Intents.release()
    }

    @Test
    fun selectProfileTest() {
        addProfileButton.perform(click())

        onData(anything()).
                inAdapterView(withId(R.id.profile_list_view)).
                atPosition(0).
                check(matches(isDisplayed())).perform(click())

        intended(hasComponent(BluetoothPage::class.java.name))
    }
}