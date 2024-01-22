package io.github.forceload.overlayer.property.type

open class Property<T>(open var value: T?) {
    override fun toString(): String = value.toString()

    fun formatFloatDigit(digit: Int) = if (value is Number) {
        String.format("%.${digit}f", value)
    } else throw UnsupportedOperationException("Numeric formatting of non-numeric values is not supported")

    fun formatDigit(digit: Int, fill: Char = '0') = if (value is Number) {
        ""
    } else throw UnsupportedOperationException("Numeric formatting of non-numeric values is not supported")
}