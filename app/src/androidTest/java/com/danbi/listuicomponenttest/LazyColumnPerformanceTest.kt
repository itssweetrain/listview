package com.danbi.listuicomponenttest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.danbi.listuicomponenttest.ui.LazyColumnActivity
import org.junit.Rule
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader

class LazyColumnPerformanceTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<LazyColumnActivity>()

    @Test
    fun testLazyColumnPerformance() {
        composeTestRule.waitForIdle()

        val positionToCheck = 20

        repeat(100 / 20) { i ->
            val position = i * 20

            composeTestRule.onNodeWithText("Item $position").performScrollTo()

            measurePerformance(position)

            Thread.sleep(500)
        }
    }

    private fun measurePerformance(position: Int) {
        val runtime = Runtime.getRuntime()
        val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
        val cpuUsage = getCpuUsage()

        android.util.Log.d("Profiler", "Position $position: Memory ${usedMemory}MB, CPU ${cpuUsage}%")
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
