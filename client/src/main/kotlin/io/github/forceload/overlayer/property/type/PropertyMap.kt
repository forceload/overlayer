package io.github.forceload.overlayer.property.type

import java.util.InvalidPropertiesFormatException

class PropertyMap<T>: HashMap<T, Property<*>>() {
    operator fun set(key: T, value: Any?): Property<*>? {
        return when (value) {
            is String? -> super.put(key, StringProperty(value))
            is Double? -> super.put(key, DoubleProperty(value))
            else -> throw InvalidPropertiesFormatException("Property Type is Invalid: $key")
        }
    }
}