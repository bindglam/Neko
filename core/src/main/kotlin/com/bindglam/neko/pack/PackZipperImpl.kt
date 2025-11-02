package com.bindglam.neko.pack

import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.api.pack.PackFile
import com.bindglam.neko.api.pack.PackZipper
import com.bindglam.neko.api.pack.PackComponent
import com.bindglam.neko.utils.createIfNotExists
import com.bindglam.neko.utils.getRelativePath
import com.bindglam.neko.utils.listFilesRecursively
import com.bindglam.neko.utils.parallelIOThreadPool
import com.bindglam.neko.utils.toPackFile
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class PackZipperImpl(private val buildFile: File) : PackZipper {
    private val pool = parallelIOThreadPool()

    private val entries = hashMapOf<String, PackFile>()

    override fun addFile(name: String, file: PackFile) {
        entries[name] = file
    }

    override fun addDirectory(folder: File) {
        folder.listFilesRecursively().forEach {
            entries[it.getRelativePath(folder.path, "/")] = it.toPackFile()
        }
    }

    override fun addComponent(path: String, component: PackComponent) {
        component.apply(path, this)
    }

    override fun file(path: String): PackFile? = entries[path]

    override fun build(process: Process) {
        buildFile.createIfNotExists()

        ZipOutputStream(FileOutputStream(buildFile)).apply {
        }.use { zipStream ->
            process.forEachParallel(entries.entries.toList(), { file -> file.value.size() }) { entry ->
                val bytes = entry.value.cachedData() ?: entry.value.data()!!.get()

                synchronized(zipStream) {
                    zipStream.putNextEntry(ZipEntry(entry.key))
                    zipStream.write(bytes)
                    zipStream.closeEntry()
                }
            }
        }
    }

    override fun close() {
        pool.close()
    }
}