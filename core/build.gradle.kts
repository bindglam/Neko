import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    id("paper-conventions")
    alias(libs.plugins.resourceFactory.paper)
}

repositories {
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    implementation("de.tr7zw:item-nbt-api:2.15.2")
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:10.1.2")
    implementation(project(":api"))
}

paperPluginYaml {
    name = rootProject.name
    version = rootProject.version.toString()
    main = "$group.NekoPlugin"
    loader = "$group.NekoLoader"
    apiVersion = "1.21.4"
    author = "Bindglam"
    dependencies {
    }
}