plugins {
    id("standard-conventions")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    compileOnly(project(":api"))
}