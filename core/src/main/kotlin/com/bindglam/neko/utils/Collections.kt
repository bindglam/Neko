package com.bindglam.neko.utils

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

// ParallelIOThreadPool By toxicity188
fun parallelIOThreadPool() = try {
    ParallelIOThreadPool()
} catch (error: OutOfMemoryError) {
    throw RuntimeException("You have to set your Linux max thread limit!", error)
}


// ParallelIOThreadPool By toxicity188
class ParallelIOThreadPool : AutoCloseable {
    private val available = Runtime.getRuntime().availableProcessors() * 2
    private val integer = AtomicInteger()

    private val dispatcher = Executors.newFixedThreadPool(available) {
        Thread(it).apply {
            isDaemon = true
            name = "Neko-Worker-${integer.andIncrement}"
        }
    }.asCoroutineDispatcher()

    override fun close() {
        dispatcher.close()
    }
    
    fun <T> forEachParallel(list: List<T>, block: (T) -> Unit) {
        if (list.isEmpty()) return

        runBlocking {
            coroutineScope {
                list.forEach { item ->
                    launch(dispatcher) {
                        block(item)
                    }
                }
            }
        }
    }
}