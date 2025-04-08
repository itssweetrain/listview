package com.danbi.listuicomponenttest

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.danbi.listuicomponenttest.ui.RecyclerViewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader

@RunWith(AndroidJUnit4::class)
class RecyclerViewPerformanceTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RecyclerViewActivity::class.java)

    @Test
    fun testRecyclerViewPerformance() {
        val recyclerViewId = R.id.list
        val positionToCheck = 20

        repeat(100 / 20) { i ->
            val position = i * 20

            onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))

            measurePerformance(position)

            Thread.sleep(500)
        }
    }

    private fun measurePerformance(position: Int) {
        val runtime = Runtime.getRuntime()
        val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
        val cpuUsage = getCpuUsage()

        Log.d(
            "Profiler",
            "Position $position: Memory ${usedMemory}MB, CPU ${cpuUsage}%"
        )
    }

    private fun getCpuUsage(): Double {
        return try {
            val process = Runtime.getRuntime().exec("top -n 1")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var cpuUsage = -1.0

            reader.use {
                it.lineSequence().forEach { line ->
                    if (line.contains("User") && line.contains("System")) {
                        val values = line.split(",")
                        val userCpu = values[0].replace(Regex("[^0-9.]"), "").toDoubleOrNull() ?: 0.0
                        val systemCpu = values[1].replace(Regex("[^0-9.]"), "").toDoubleOrNull() ?: 0.0
                        cpuUsage = userCpu + systemCpu
                    }
                }
            }
            cpuUsage
        } catch (e: Exception) {
            e.printStackTrace()
            -1.0
        }
    }
}
