package io.github.forceload.overlayer.gui

import io.github.forceload.overlayer.property.type.DoubleProperty
import io.github.forceload.overlayer.property.type.PropertyMap
import kotlin.math.*

class FormattedText(var text: String, var property: PropertyMap<String> = PropertyMap()) {
    override fun toString(): String {
        var bracketsOpen = 0
        var bracketOpenIndex = 0
        var bracketLength = 0
        val result = StringBuilder()

        text.forEachIndexed { index, char ->
            if (bracketsOpen > 0) if (char == '}') {
                if (--bracketsOpen <= 0) result.append(property.convertProperty(text.slice(bracketOpenIndex + 1..<index)))
            } else bracketLength++ else if (char != '{') result.append(char)

            if (char == '{' && bracketsOpen++ <= 0) bracketOpenIndex = index
        }

        return result.toString()
    }
}

fun String.roundNum(digit: Int): Pair<Boolean, String> = if (digit != 0) {
        var result = this.slice(0 until digit).toBigInteger()
        if (this.length > digit && this[digit].digitToInt() >= 5) result++ // Round Number in Float Part

        val str = result.toString()

        if (str.length > digit) Pair(true, str.slice(1..<str.length)) // Round Number to Int Part
        else Pair(false, str)
    } else if (this.isNotEmpty()) Pair(this[0].digitToInt() >= 5, "")
    else Pair(false, "")

private val regex =
    Regex("^(?>(?<intFormatFill>.?)(?<intFormatDigit>[0-9]+):)?(?<key>[^:\\\\n]+)(?>:(?<floatFormatDigit>[0-9]+))?\$")
private fun PropertyMap<String>.convertProperty(key: String): String {
    var intFormatting = false
    var floatFormatting = false
    val result = regex.matchEntire(key) ?: return key

    val property = this[result.groups["key"]!!.value] ?: return "null"

    val converted = if (property.value is Number) {
        if (result.groups["intFormatDigit"]?.value != null) intFormatting = true
        if (result.groups["floatFormatDigit"]?.value != null) floatFormatting = true

        when (property) {
            is DoubleProperty -> {
                val fractionalPart = (property.value!! - floor(property.value!!)).toString().drop(2)
                var floored = floor(property.value!!).toInt()

                val fractionalPartStr = if (floatFormatting) {
                    val floatFormatDigit = result.groups["floatFormatDigit"]!!.value.toInt()

                    if (fractionalPart.length < floatFormatDigit) fractionalPart + "0".repeat(floatFormatDigit - fractionalPart.length)
                    else if (fractionalPart.length > floatFormatDigit) {
                        val rounded = fractionalPart.roundNum(floatFormatDigit)
                        if (rounded.first) floored++

                        rounded.second
                    } else fractionalPart
                } else fractionalPart

                val integerPart = floored.toString()
                val intPart = if (intFormatting) {
                    val intFormatFill = result.groups["intFormatFill"]?.value ?: " "
                    val intFormatDigit = result.groups["intFormatDigit"]!!.value.toInt()

                    if (integerPart.length < intFormatDigit) intFormatFill.repeat(intFormatDigit - integerPart.length) + integerPart
                    else integerPart
                } else integerPart

                "$intPart${if (fractionalPartStr.isNotEmpty()) "." else ""}$fractionalPartStr"
            }
            else -> String.format("%${if (intFormatting) "${result.groups["intFormatFill"]?.value ?: ""}${result.groups["intFormatDigit"]!!.value}" else ""}d", property.value)
        }
    } else property.toString()

    return converted
}