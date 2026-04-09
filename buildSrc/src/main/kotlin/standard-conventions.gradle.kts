plugins {
    id("java-library")
    kotlin("jvm")
}

group = "io.github.bindglam.neko"
version = property("plugin_version").toString()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.44")
    annotationProcessor("org.projectlombok:lombok:1.18.44")
    compileOnly("net.kyori:adventure-api:4.26.1")
    implementation("it.unimi.dsi:fastutil:8.5.18")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("com.google.guava:guava:33.5.0-jre")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

kotlin {
    jvmToolchain(25)
}