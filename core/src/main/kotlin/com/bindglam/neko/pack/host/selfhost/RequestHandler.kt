package com.bindglam.neko.pack.host.selfhost

import com.bindglam.neko.api.manager.PackManager
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

class RequestHandler : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        PackManager.BUILD_ZIP.inputStream().readBytes().also { bytes ->
            exchange.sendResponseHeaders(200, bytes.size.toLong())

            val outputStream = exchange.responseBody
            outputStream.write(bytes)
            outputStream.flush()
        }
    }
}