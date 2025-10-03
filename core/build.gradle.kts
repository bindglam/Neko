import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
    id("paper-conventions")
    alias(libs.plugins.resourceFactory.paper)
}

repositories {
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    rootProject.project("nms").subprojects.forEach {
        implementation(project(":nms:${it.name}"))
    }
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
}