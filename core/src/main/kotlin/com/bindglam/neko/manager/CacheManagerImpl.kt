package com.bindglam.neko.manager

import com.bindglam.neko.api.data.Cache
import com.bindglam.neko.api.manager.CacheManager
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.data.CacheImpl
import org.slf4j.LoggerFactory
import java.io.File

object CacheManagerImpl : CacheManager {
    private val LOGGER = LoggerFactory.getLogger(CacheManager::class.java)

    private val CACHE_FOLDER = File("plugins/Neko/cache")

    private val cacheMap = hashMapOf<String, Cache>()

    override fun start(process: Process) {
        if(!CACHE_FOLDER.exists())
            CACHE_FOLDER.mkdirs()
    }

    override fun end(process: Process) {
        cacheMap.forEach { name, cache -> cache.save() }
    }

    override fun getCache(name: String): Cache = cacheMap.computeIfAbsent(name) { CacheImpl.load(name) }
}