package io.github.forceload.overlayer.property.type

import java.util.InvalidPropertiesFormatException

class PropertyMap<T>: HashMap<T, Property<*>>() {
    operator fun set(key: T, value: Any?): Property<*>? {
        return when (value) {
            is String? -> super.put(key, StringProperty(value))
            is Double? -> super.put(key, DoubleProperty(value))
            is Int? -> super.put(key, IntProperty(value))

            is Property<*>? -> value?.let { super.put(key, it) }

            else -> throw InvalidPropertiesFormatException("Property Type is Invalid: $key")
        }
    }
}