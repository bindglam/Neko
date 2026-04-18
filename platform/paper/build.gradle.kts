import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("platform-conventions")
    alias(libs.plugins.resourceFactory.paper)
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

val groupString = group.toString()
val versionString = version.toString()
val mcVersionString = property("minecraft_version").toString()

dependencies {
    compileOnly("io.papermc.paper:paper-api:$mcVersionString.build.+")
    compileOnly(libs.cloud.paper)
}

paperPluginYaml {
    name = rootProject.name
    version = rootProject.version.toString()
    main = "$group.NekoPaperPlugin"
    loader = "$group.NekoPluginLoader"
    apiVersion = "26.1"
    author = "Bindglam"
    foliaSupported = true
    dependencies {
    }
}

val runServerAction = Action<RunServer> {
    version(mcVersionString)

    downloadPlugins {
        /*pluginJars(project("test-plugin").tasks.shadowJar.flatMap {
            it.archiveFile
        })*/
    }
}

runPaper.folia.registerTask(op = runServerAction)

tasks {
    runServer {
        runServerAction.execute(this)
    }

    jar {
        finalizedBy(shadowJar)
    }

    shadowJar {
        archiveClassifier = ""

        dependencies {
            exclude(dependency("org.jetbrains:annotations:13.0")); exclude(dependency("org.jetbrains:annotations:23.0.0")); exclude(dependency("org.jetbrains:annotations:26.0.2"))
        }

        fun prefix(pattern: String) {
            relocate(pattern, "$groupString.shaded.$pattern")
        }
        prefix("kotlin")
        prefix("org.bstats")
    }
}