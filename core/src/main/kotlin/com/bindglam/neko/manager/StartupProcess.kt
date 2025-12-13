package com.bindglam.neko.manager

import com.bindglam.neko.api.manager.LifecycleContext
import com.bindglam.neko.api.manager.Managerial
import com.bindglam.neko.api.manager.Process
import com.bindglam.neko.utils.parallelIOThreadPool
import java.util.function.Consumer
import java.util.function.Function

class StartupProcess : Process {
    private val parallelThreadPool = parallelIOThreadPool()

    override fun start(context: LifecycleContext, list: List<Managerial>) {
        list.forEach {
            it.start(context, this)
        }
    }

    override fun close() {
        parallelThreadPool.close()
    }

    override fun <T : Any> forEachParallel(list: List<T>, sizeAssume: Function<T, Long>, block: Consumer<T>) {
        parallelThreadPool.forEachParallel(list, { e -> sizeAssume.apply(e) }) { block.accept(it) }
    }
}