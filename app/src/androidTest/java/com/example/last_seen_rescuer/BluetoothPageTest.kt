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

    private val addLogDataButton = onView(withId(R.id.get_data_button))

    @Before
    fun initBundle() {
        val intent = Intent()

        intent.putExtra("profile", "TEST")
        bluetoothPageTestRule.launchActivity(intent)
    }

    @Test
    fun getLogDataTest() {
        addLogDataButton.perform(click())

        Espresso.onData(CoreMatchers.anything()).
            inAdapterView(withId(R.id.bluetooth_checkpoint_data_list_view)).
            atPosition(0).
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}