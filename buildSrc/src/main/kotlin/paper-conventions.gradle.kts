plugins {
    id("standard-conventions")
}

val minecraftVersion = property("minecraft_version").toString()
dependencies {
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")
}