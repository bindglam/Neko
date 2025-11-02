package com.bindglam.neko.utils

import kotlinx.coroutines.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun parallelIOThreadPool() = try {
    ParallelIOThreadPool()
} catch (error: OutOfMemoryError) {
    throw RuntimeException("You have to set your Linux max thread limit!", error)
}

// ParallelIOThreadPool(Inspired by toxicity188)
class ParallelIOThreadPool : AutoCloseable {
    private val available = Runtime.getRuntime().availableProcessors() * 2
    private val pool = Executors.newFixedThreadPool(available) { runnable ->
        Thread(runnable).apply {
            isDaemon = true
            name = "Neko-IO-Worker-${Thread.currentThread().threadGroup.activeCount()}"
        }
    }

    override fun close() {
        pool.shutdown()
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow()
            }
        } catch (e: InterruptedException) {
            pool.shutdownNow()
            Thread.currentThread().interrupt()
        }
    }

    fun <T> forEachParallel(list: List<T>, sizeAssume: (T) -> Long, block: (T) -> Unit) {
        if (list.isEmpty()) return

        val size = list.size

        // 작은 리스트는 병렬 처리 오버헤드가 더 클 수 있음
        if (size < available) {
            list.forEach(block)
            return
        }

        val tasks = if (available >= size) {
            // 각 요소를 개별 태스크로
            list.map { item ->
                CompletableFuture.runAsync({ block(item) }, pool)
            }
        } else {
            // 청크로 분할하여 처리
            val chunkSize = (size + available - 1) / available // 올림 나눗셈
            val sortedList = list.sortedByDescending(sizeAssume) // 큰 것부터 정렬

            sortedList.chunked(chunkSize).map { chunk ->
                CompletableFuture.runAsync({
                    chunk.forEach(block)
                }, pool)
            }
        }

        CompletableFuture.allOf(*tasks.toTypedArray()).join()
    }
}