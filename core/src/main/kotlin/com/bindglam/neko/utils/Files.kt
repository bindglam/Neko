package com.bindglam.neko.utils

import com.bindglam.neko.pack.PackFile
import java.io.File
import java.nio.file.Files

fun File.createIfNotExists() {
    if(!parentFile.exists())
        parentFile.mkdirs()
    if(!exists())
        createNewFile()
}

fun File.listFilesRecursively(): List<File> = ArrayList<File>().apply {
    listFiles().forEach { file ->
        if(file.isDirectory)
            addAll(file.listFilesRecursively())
        else
            add(file)
    }
}

fun File.getRelativePath(root: String, newRoot: String, separator: String): String {
    val rootPath = if(newRoot.isEmpty()) "" else "${newRoot}${separator}"

    return "${rootPath}${if(path.startsWith(root)) path.substring(root.length+1).replace(File.separator, separator) else path.replace(File.separator, separator)}"
}

fun File.getRelativePath(root: String, separator: String): String = getRelativePath(root, "", separator)

fun File.getRelativePath(root: String): String = getRelativePath(root, File.separator)

fun File.toPackFile(): PackFile = PackFile({ inputStream().readBytes() }, Files.size(toPath()))