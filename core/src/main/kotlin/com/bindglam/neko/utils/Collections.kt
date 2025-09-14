package com.bindglam.neko.utils

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

// ParallelIOThreadPool By toxicity188
fun parallelIOThreadPool() = try {
    ParallelIOThreadPool()
} catch (error: OutOfMemoryError) {
    throw RuntimeException("You have to set your Linux max thread limit!", error)
}


// ParallelIOThreadPool By toxicity188
class ParallelIOThreadPool : AutoCloseable {
    private val available = Runtime.getRuntime().availableProcessors() * 2

    @OptIn(DelicateCoroutinesApi::class)
    private val pool = newFixedThreadPoolContext(available, "Neko-Worker-Dispatcher")

    override fun close() {
        pool.close()
    }

    fun <T> forEachParallel(list: List<T>, sizeAssume: (T) -> Long, block: (T) -> Unit) {
        if (list.isEmpty()) return
        val size = list.size
        val lastIndex = list.lastIndex
        val tasks = if (available >= size) {
            list.map {
                {
                    block(it)
                }
            }
        } else {
            val sorted = list.sortedBy(sizeAssume)
            val queue = arrayListOf<() -> Unit>()
            var i = 0
            val add = (size.toDouble() / available).toInt()
            while (i <= size) {
                val list = ArrayList<T>(add)
                for (t in i..<(i + add).coerceAtMost(size)) {
                    val ht = t / 2
                    list += sorted[if (t % 2 == 0) ht else lastIndex - ht]
                }
                queue += {
                    list.forEach(block)
                }
                i += add
            }
            queue
        }

        runBlocking {
            tasks.map { async(pool) { it() } }.awaitAll()
        }
    }
}