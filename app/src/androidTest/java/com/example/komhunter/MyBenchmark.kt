package com.example.komhunter

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun myFunctionBenchmark() {
        // Define the block of code to benchmark
        benchmarkRule.measureRepeated {
            // Call the function to benchmark
            myExpensiveFunction()
        }
    }

    // Example function to benchmark
    private fun myExpensiveFunction() {
        // Perform some expensive operations
        Thread.sleep(50) // Simulate a time-consuming operation
    }
}
