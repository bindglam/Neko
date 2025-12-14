import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("paper-conventions")
    alias(libs.plugins.resourceFactory.paper)
}

repositories {
    maven("https://repo.extendedclip.com/releases/")
}

dependencies {
    implementation(project(":api"))
    rootProject.project("nms").subprojects.forEach {
        implementation(project(":nms:${it.name}"))
    }

    compileOnly("me.clip:placeholderapi:2.11.7")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.1.0")
}

paperPluginYaml {
    name = rootProject.name
    version = rootProject.version.toString()
    main = "$group.NekoPlugin"
    loader = "$group.NekoLoader"
    bootstrapper = "$group.NekoBootstrap"
    apiVersion = "1.21"
    author = "Bindglam"
    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
    foliaSupported = true
    dependencies {
        server(name = "PlaceholderAPI", load = PaperPluginYaml.Load.BEFORE, required = false, joinClasspath = true)
        server(name = "MiniPlaceholders", load = PaperPluginYaml.Load.BEFORE, required = false, joinClasspath = true)
    }
}