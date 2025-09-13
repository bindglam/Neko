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

        private const val DEFAULT_HOSTNAME = "localhost"
        private const val DEFAULT_PORT = 1980
    }

    private lateinit var httpServer: HttpServer

    private lateinit var uri: URI

    override fun start(config: ConfigurationSection) {
        LOGGER.info("Starting self-host...")

        uri = URI.create("http://${config.getString("host", DEFAULT_HOSTNAME)}:${config.getInt("port", DEFAULT_PORT)}")

        httpServer = HttpServer.create(InetSocketAddress(uri.port), 0)
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
            .packs(NekoProvider.neko().packManager().packInfo(uri)).build())
    }

    override fun id(): String = "selfhost"
}