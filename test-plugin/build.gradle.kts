import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("paper-conventions")
    alias(libs.plugins.resourceFactory.paper)
}

val pluginName = "${rootProject.name}-TestPlugin"

dependencies {
    compileOnly(project(":api"))
}

tasks {
    jar {
        archiveBaseName = pluginName
    }
}

paperPluginYaml {
    name = pluginName
    version = project.version.toString()
    main = "$group.test.NekoTestPlugin"
    apiVersion = "1.21"
    author = "Bindglam"
    authors = listOf("Bindglam")
    load = BukkitPluginYaml.PluginLoadOrder.POSTWORLD
    foliaSupported = true
    dependencies {
        server("Neko", PaperPluginYaml.Load.BEFORE, true, true)
    }
}