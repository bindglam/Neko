package com.bindglam.neko.pack

import com.bindglam.neko.manager.PackManagerImpl
import com.bindglam.neko.utils.createIfNotExists
import com.bindglam.neko.utils.getRelativePath
import com.bindglam.neko.utils.listFilesRecursively
import com.bindglam.neko.utils.parallelIOThreadPool
import com.bindglam.neko.utils.toPackFile
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.util.concurrent.atomic.AtomicInteger
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class PackZipper(private val buildFile: File) {
    private val parallelThreadPool = parallelIOThreadPool()

    private val folders = hashSetOf<String>()
    private val entries = hashMapOf<String, PackFile>()

    fun addFile(name: String, file: PackFile) {
        entries[name] = file
    }

    fun addFile(file: File) {
        addFile(file.name, file.toPackFile())
    }

    fun addDirectory(folder: File) {
        folder.listFilesRecursively().forEach {
            if(it.parentFile.path != folder.path)
                folders.add(it.parentFile.getRelativePath(folder.path, "/"))

            entries[it.getRelativePath(folder.path, "/")] = it.toPackFile()
        }
    }

    fun build(onDone: Runnable) {
        buildFile.createIfNotExists()

        ZipOutputStream(FileOutputStream(buildFile)).apply {
        }.use { zipStream ->
            folders.forEach { path ->
                zipStream.putNextEntry(ZipEntry("${path}/"))
                zipStream.closeEntry()
            }

            var cnt = AtomicInteger()

            parallelThreadPool.forEachParallel(entries.entries.toList(), { it.value.size }) { entry ->
                val bytes = entry.value.bytes()

                synchronized(zipStream) {
                    zipStream.putNextEntry(ZipEntry(entry.key))
                    zipStream.write(bytes)
                    zipStream.closeEntry()

                    if(cnt.incrementAndGet() >= entries.size) {
                        onDone.run()
                    }
                }
            }
        }
    }
}