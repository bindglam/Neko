package com.bindglam.neko.data

import com.bindglam.neko.api.data.Cache
import com.bindglam.neko.utils.createIfNotExists
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class CacheImpl(private val name: String, private val map: YamlConfiguration) : Cache {
    companion object {
        fun load(name: String): Cache {
            val file = File(Cache.CACHE_FOLDER, "$name.yml")

            if(!file.exists())
                return CacheImpl(name)

            return CacheImpl(name, YamlConfiguration.loadConfiguration(file))
        }
    }

    constructor(name: String) : this(name, YamlConfiguration())

    override fun save() {
        if(!Cache.CACHE_FOLDER.exists())
            Cache.CACHE_FOLDER.mkdirs()

        val file = File(Cache.CACHE_FOLDER, "$name.yml")
        file.createIfNotExists()

        map.save(file)
    }

    override fun set(key: String, value: Any?) {
        value ?: return

        map.set(key, value)
    }

    override fun get(key: String): Any? = map.get(key)
    override fun getString(key: String): String? = map.getString(key)
    override fun getCharacter(key: String): Char? = getString(key)?.get(0)
    override fun getNumber(key: String): Number? = get(key) as Number?
}