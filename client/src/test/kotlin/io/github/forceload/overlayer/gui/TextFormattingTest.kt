package io.github.forceload.overlayer.gui

import io.github.forceload.overlayer.property.type.PropertyMap
import java.text.Format
import kotlin.system.measureNanoTime
import kotlin.test.Test

class TextFormattingTest {
    private fun testFormat(text: String, properties: PropertyMap<String>, result: String) {
        val str = FormattedText(text, properties).toString()
        println(str); assert(str == result)
    }

    private fun testPerformance(text: String, properties: PropertyMap<String>) {
        val text = FormattedText(text, properties)

        val str: String
        val time = measureNanoTime { str = text.toString() }
        println("${time}ns; $str"); assert(time <= 100000)
    }

    @Test fun doubleFormattingTest() {
        val properties = PropertyMap<String>()
        properties["test1"] = 0.01
        properties["test2"] = 0.99

        testFormat("hi {test1}", properties, "hi 0.01")
        testFormat("hi {test2}", properties, "hi 0.99")

        testFormat("hi {test1:1}", properties, "hi 0.0")
        testFormat("hi {test2:1}", properties, "hi 1.0")

        testPerformance("hi {test1:1}", properties)
        testPerformance("hi {test2:1}", properties)

        testFormat("hi {02:test1:1}", properties, "hi 00.0")
        testFormat("hi {02:test2:1}", properties, "hi 01.0")

        testFormat("hi {02:test1:2}", properties, "hi 00.01")
        testFormat("hi {02:test2:2}", properties, "hi 00.99")

        testPerformance("hi {02:test1:2}", properties)
        testPerformance("hi {02:test2:2}", properties)

        testFormat("hi {02:test1:3}", properties, "hi 00.010")
        testFormat("hi {02:test2:3}", properties, "hi 00.990")

        testFormat("hi {02:test1:0}", properties, "hi 00")
        testFormat("hi {02:test2:0}", properties, "hi 01")
    }
}