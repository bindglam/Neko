package com.bindglam.neko.utils

import kotlinx.coroutines.*

fun parallelIOThreadPool() = try {
    ParallelIOThreadPool()
} catch (error: OutOfMemoryError) {
    throw RuntimeException("You have to set your Linux max thread limit!", error)
}

// ParallelIOThreadPool(Inspired by toxicity188)
class ParallelIOThreadPool : AutoCloseable {
    private val available = Runtime.getRuntime().availableProcessors() * 2

    @OptIn(DelicateCoroutinesApi::class)
    private val dispatcher = newFixedThreadPoolContext(
        available,
        name = "Neko-Thread-Pool"
    )

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