plugins {
    id("standard-conventions")
}

dependencies {
    api(project(":api"))
    api("net.kyori:adventure-text-minimessage:4.26.1")
    api("team.unnamed:creative-serializer-minecraft:1.7.3")
    api("team.unnamed:creative-server:1.7.3")
}
