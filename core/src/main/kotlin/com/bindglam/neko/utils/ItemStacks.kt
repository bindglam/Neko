package com.bindglam.neko.utils

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

object NamespacedKeyDataType : PersistentDataType<String, NamespacedKey> {
    override fun getPrimitiveType(): Class<String> = String::class.java
    override fun getComplexType(): Class<NamespacedKey> = NamespacedKey::class.java
    override fun toPrimitive(complex: NamespacedKey, content: PersistentDataAdapterContext): String = complex.asString()
    override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext): NamespacedKey = NamespacedKey.fromString(primitive)!!
}