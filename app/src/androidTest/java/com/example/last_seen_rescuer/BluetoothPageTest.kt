package com.example.last_seen_rescuer

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BluetoothPageTest {
    @Rule
    @JvmField
    val bluetoothPageTestRule = ActivityTestRule(BluetoothPage::class.java, false, false)

    private val requestLogDataButton = onView(withId(R.id.get_data_button))

    @Before
    fun initBundle() {
        val intent = Intent()

        intent.putExtra("firstName", "First")
        intent.putExtra("lastName", "Last")
        intent.putExtra("dateOfBirth", "11/11/1111")
        intent.putExtra("userIdentifier", "bqlmFPzO7lX2dbeRZ8yBugl2c4u2")
        intent.putExtra("macAddress", "48:A4:72:54:72:BA")
        bluetoothPageTestRule.launchActivity(intent)
    }

    @Test
    fun itineraryTest() {
        Thread.sleep(1000)

        Espresso.onData(CoreMatchers.anything()).
            inAdapterView(withId(R.id.itinerary_list_view)).
            atPosition(0).
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun getLogDataTest() {
        Thread.sleep(1000)

        requestLogDataButton.perform(click())

        Thread.sleep(10000)

        Espresso.onData(CoreMatchers.anything()).
            inAdapterView(withId(R.id.bluetooth_checkpoint_data_list_view)).
            atPosition(0).
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}