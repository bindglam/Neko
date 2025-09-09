package com.bindglam.neko.utils

import com.bindglam.neko.api.content.MechanismFactory
import com.bindglam.neko.api.registry.Registry
import net.kyori.adventure.key.Key

inline fun <reified T> Registry<MechanismFactory<*>>.getFactory(key: Key?): MechanismFactory<T>? {
    key ?: return null

    val factory = getOrNull(key) ?: return null

    if(factory.type() != T::class.java)
        return null

    return factory as MechanismFactory<T>
}