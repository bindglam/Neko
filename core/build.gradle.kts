plugins {
    id("paper-conventions")
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
}

dependencies {
    implementation(project(":api"))
    compileOnly("com.github.bindglam:ConfigLib:1.0.0")
    compileOnly("org.incendo:cloud-paper:2.0.0-beta.14")
    //compileOnly("org.incendo:cloud-minecraft-extras:2.0.0-beta.14")
    compileOnly("team.unnamed:creative-api:1.7.3")
    compileOnly("team.unnamed:creative-serializer-minecraft:1.7.3")
    compileOnly("team.unnamed:creative-server:1.7.3")
}

paperPluginYaml {
    name = rootProject.name
    version = rootProject.version.toString()
    main = "$group.NekoPluginImpl"
    loader = "$group.NekoPluginLoader"
    apiVersion = "1.20"
    author = "Bindglam"
    foliaSupported = true
    dependencies {
    }
}