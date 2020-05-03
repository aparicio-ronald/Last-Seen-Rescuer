package com.example.last_seen_rescuer

import android.content.Intent
import androidx.test.espresso.Espresso.onData
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

    @Before
    fun initIntentAndBundle() {
        Intents.init()

        val intent = Intent()

        intent.putExtra("firstName", "First")
        intent.putExtra("lastName", "Last")
        intent.putExtra("dateOfBirth", "")
        intent.putExtra("streetAddress", "")
        intent.putExtra("city", "")
        intent.putExtra("state", "")
        intent.putExtra("zipCode", "")

        selectionPageTestRule.launchActivity(intent)
    }

    @After
    fun releaseIntent() {
        Intents.release()
    }

    @Test
    fun selectProfileTest() {
        Thread.sleep(1000)

        onData(anything()).
                inAdapterView(withId(R.id.profile_list_view)).
                atPosition(0).
                check(matches(isDisplayed())).perform(click())

        intended(hasComponent(BluetoothPage::class.java.name))
    }
}