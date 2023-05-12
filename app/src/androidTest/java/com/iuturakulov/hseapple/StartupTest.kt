package com.iuturakulov.hseapple

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.iuturakulov.hseapple.ui.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testColdStart() {
        val beforeStart = System.currentTimeMillis()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        val afterStart = System.currentTimeMillis()

        val startupTime = afterStart - beforeStart
        Log.d("StartupTest", "Cold start time: $startupTime ms")
    }

    @Test
    fun testHotStart() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.STARTED)

        val beforeStart = System.currentTimeMillis()
        activityScenario.moveToState(Lifecycle.State.RESUMED)
        val afterStart = System.currentTimeMillis()

        val startupTime = afterStart - beforeStart
        Log.d("StartupTest", "Hot start time: $startupTime ms")
    }
}
