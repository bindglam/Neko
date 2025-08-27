plugins {
    id("paper-conventions")
    alias(libs.plugins.resourceFactory.paper)
}

repositories {
}

dependencies {
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
}