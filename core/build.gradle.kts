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
    rootProject.project("nms").subprojects.forEach {
        implementation(project(":nms:${it.name}"))
    }

    implementation("com.github.ben-manes.caffeine:caffeine:3.2.2")
}

paperPluginYaml {
    name = rootProject.name
    version = rootProject.version.toString()
    main = "$group.NekoPlugin"
    apiVersion = "1.21"
    author = "Bindglam"
}