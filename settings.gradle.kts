pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net") { name = "FabricMC" }

        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "overlayer"
include("client")
