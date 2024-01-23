package io.github.forceload.overlayer.gui

import io.github.forceload.overlayer.property.type.DoubleProperty
import io.github.forceload.overlayer.property.type.PropertyMap

class FormattedText(var text: String, var property: PropertyMap<String> = PropertyMap()) {
    override fun toString(): String {
        var bracketsOpen = 0
        var bracketOpenIndex = 0
        var bracketLength = 0
        val result = StringBuilder()

        text.forEachIndexed { index, char ->
            if (bracketsOpen > 0) if (char == '}') {
                if (--bracketsOpen <= 0) result.append(property.getRight(text.slice(bracketOpenIndex + 1..<index)))
            } else bracketLength++ else if (char != '{') result.append(char)

            if (char == '{' && bracketsOpen++ <= 0) bracketOpenIndex = index
        }

        return result.toString()
    }
}

private val regex =
    Regex("^(?>(?<intFormatFill>.?)(?<intFormatDigit>[0-9]+):)?(?<key>[^:\\\\n]+)(?>:(?<floatFormatDigit>[0-9]+))?\$")
fun PropertyMap<String>.getRight(key: String): String {
    var intFormatting = false
    var floatFormatting = false
    val result = regex.matchEntire(key) ?: return key

    val property = this[result.groups["key"]!!.value] ?: return "null"

    val converted = if (property.value is Number) {
        if (result.groups["intFormatDigit"]?.value != null) intFormatting = true
        if (result.groups["floatFormatDigit"]?.value != null) floatFormatting = true

        when (property) {
            is DoubleProperty -> {
                println("%${if (intFormatting) "${result.groups["intFormatFill"]?.value ?: ""}${result.groups["intFormatDigit"]!!.value}" else ""}${if (floatFormatting) ".${result.groups["floatFormatDigit"]!!.value}f" else "f"}")
                String.format("%${if (intFormatting) "${result.groups["intFormatFill"]?.value ?: ""}${result.groups["intFormatDigit"]!!.value}" else ""}${if (floatFormatting) ".${result.groups["floatFormatDigit"]!!.value}f" else "f"}", property.value)
            }
            else -> String.format("%${if (intFormatting) "${result.groups["intFormatFill"]?.value ?: ""}${result.groups["intFormatDigit"]!!.value}" else ""}d", property.value)
        }
    } else property.toString()

    return converted
}