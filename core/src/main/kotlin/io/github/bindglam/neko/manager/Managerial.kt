package io.github.bindglam.neko.manager

interface Managerial {
    fun preload(context: Context)

    fun start(context: Context)

    fun end(context: Context)
}