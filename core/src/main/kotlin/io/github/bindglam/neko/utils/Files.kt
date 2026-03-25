package io.github.bindglam.neko.utils

import java.io.File

fun File.listFilesRecursively() = walk(FileWalkDirection.TOP_DOWN).filter { it.isFile }