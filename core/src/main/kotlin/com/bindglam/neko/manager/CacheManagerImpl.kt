package com.bindglam.neko.manager

import com.bindglam.neko.api.manager.CacheManager
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream

object CacheManagerImpl : CacheManager {
    private val LOGGER = LoggerFactory.getLogger(CacheManager::class.java)

    private val CACHE_FOLDER = File("plugins/Neko/cache")

    override fun start() {
        if(!CACHE_FOLDER.exists())
            CACHE_FOLDER.mkdirs()
    }

    override fun end() {
    }

    override fun getCache(path: String): InputStream? {
        File(CACHE_FOLDER, path).also { return if(it.exists()) it.inputStream() else null }
    }
}