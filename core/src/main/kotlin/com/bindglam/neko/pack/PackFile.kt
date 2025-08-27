package com.bindglam.neko.pack

data class PackFile(val bytes: () -> ByteArray, val size: Long)