package com.bindglam.neko.pack.host.selfhost

import com.bindglam.neko.api.NekoProvider
import com.bindglam.neko.api.pack.host.PackHost
import com.bindglam.neko.utils.plugin
import com.sun.net.httpserver.HttpServer
import net.kyori.adventure.resource.ResourcePackInfo
import net.kyori.adventure.resource.ResourcePackRequest
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.net.URI

class SelfHost : PackHost {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(SelfHost::class.java)
    }

    private lateinit var httpServer: HttpServer

    private var hostname: String = "localhost"
    private var port: Int = 1980

    override fun start(config: ConfigurationSection) {
        hostname = config.getString("host", hostname)!!
        port = config.getInt("port", port)

        LOGGER.info("Starting self-host...")

        httpServer = HttpServer.create(InetSocketAddress(port), 0)
        httpServer.createContext("/", RequestHandler())

        Bukkit.getAsyncScheduler().runNow(NekoProvider.neko().plugin()) {
            httpServer.start()
        }
    }

    override fun end() {
        LOGGER.info("Stopping self-host...")

        httpServer.stop(1)
    }

    override fun sendPack(player: Player, message: Component) {
        player.sendResourcePacks(ResourcePackRequest.resourcePackRequest().prompt(message).required(true)
            .packs(ResourcePackInfo.resourcePackInfo(NekoProvider.neko().packManager().packId(), URI.create("http://${hostname}:${port}"), NekoProvider.neko().packManager().packHash())).build())
    }

    override fun id(): String = "selfhost"
}