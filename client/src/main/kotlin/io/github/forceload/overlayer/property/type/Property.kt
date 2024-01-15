package io.github.forceload.overlayer.property.type

open class Property<T>(open var value: T?) {
    override fun toString(): String = value.toString()
}