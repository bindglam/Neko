package com.bindglam.neko.manager

import com.bindglam.neko.api.manager.CacheManager
import com.bindglam.neko.utils.createIfNotExists
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream
import java.util.function.Consumer

object CacheManagerImpl : CacheManager {
    private val LOGGER = LoggerFactory.getLogger(CacheManager::class.java)

    private val CACHE_FOLDER = File("plugins/Neko/cache")

    override fun start() {
        if(!CACHE_FOLDER.exists())
            CACHE_FOLDER.mkdirs()
    }

    override fun end() {
    }

    override fun getCache(path: String): File? {
        File(CACHE_FOLDER, path).also { return if(it.exists()) it else null }
    }

    override fun saveCache(path: String, consumer: Consumer<File>) {
        consumer.accept(File(CACHE_FOLDER, path).apply { createIfNotExists() })
    }
}