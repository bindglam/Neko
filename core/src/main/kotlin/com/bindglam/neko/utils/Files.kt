package com.bindglam.neko.utils

import java.io.File

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