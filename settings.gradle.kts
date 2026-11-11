import java.net.URI

pluginManagement {
    // pluginManagement repositories resolve plugins before the repo-reconfiguration plugin can
    // apply, so they must point at EngineHub mirrors directly rather than upstream URLs.
    repositories {
        maven {
            name = "EngineHub"
            url = uri("https://repo.enginehub.org/libs-release/")
            mavenContent {
                releasesOnly()
                includeGroupAndSubgroups("com.sk89q")
                includeGroupAndSubgroups("org.enginehub")
            }
        }
        maven {
            name = "EngineHub FabricMC Mirror"
            url = uri("https://repo.enginehub.org/internal/fabricmc/")
            mavenContent {
                releasesOnly()
                includeGroupAndSubgroups("fabric-loom")
                includeGroupAndSubgroups("net.fabricmc")
                excludeModule("net.fabricmc", "yarn")
            }
        }
        maven {
            name = "EngineHub SpongePowered Mirror"
            url = uri("https://repo.enginehub.org/internal/spongepowered-releases/")
            mavenContent {
                releasesOnly()
                includeGroupAndSubgroups("org.spongepowered")
            }
        }
        maven {
            name = "EngineHub NeoForged Mirror"
            url = uri("https://repo.enginehub.org/internal/neoforged/")
            mavenContent {
                releasesOnly()
                includeGroupAndSubgroups("net.minecraftforge")
                includeGroupAndSubgroups("net.neoforged")
            }
        }
        maven {
            name = "EngineHub MinecraftForge Mirror"
            url = uri("https://repo.enginehub.org/internal/forge/")
            mavenContent {
                releasesOnly()
                includeGroupAndSubgroups("net.minecraftforge")
            }
        }
        maven {
            name = "EngineHub Maven Central Mirror"
            url = uri("https://repo.enginehub.org/internal/maven-central-proxy/")
            mavenContent {
                releasesOnly()
            }
        }
        maven {
            name = "EngineHub Gradle Plugin Portal Mirror"
            url = uri("https://repo.enginehub.org/internal/plugin-portal-proxy/")
            mavenContent {
                releasesOnly()
            }
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("org.enginehub.crankcase.repo-reconfiguration") version "0.1.0"
}
dependencyResolutionManagement {
    repositories {
        maven {
            name = "PaperMC"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "EngineHub (Non-Mirrored)"
            url = URI.create("https://repo.enginehub.org/libs-release/")
        }
        ivy {
            url = uri("https://repo.enginehub.org/language-files/")
            name = "EngineHub Language Files"
            patternLayout {
                artifact("[organisation]/[module]/[revision]/[artifact]-[revision](+[classifier])(.[ext])")
                setM2compatible(true)
            }
            metadataSources {
                artifact()
            }
            content {
                includeModuleByRegex(".*", "worldedit-lang")
            }
        }
        mavenCentral()
        maven {
            name = "Minecraft Libraries"
            url = uri("https://libraries.minecraft.net/")
        }
    }
}

logger.lifecycle("""
*******************************************
 You are building WorldEdit!

 If you encounter trouble:
 1) Read COMPILING.md if you haven't yet
 2) Try running 'build' in a separate Gradle run
 3) Use gradlew and not gradle
 4) If you still need help, ask on Discord! https://discord.gg/enginehub

 Output files will be in [subproject]/build/libs
*******************************************
""")

rootProject.name = "worldedit"

includeBuild("build-logic")

include("worldedit-libs")

// Thirdplace: only 26.2, the version our Canvas servers run. Each adapter sets up its own decompiled server.
listOf("26.2").forEach {
    include("worldedit-bukkit:adapters:adapter-$it")
}

// Thirdplace: only the Bukkit build; the other platforms would fetch their own toolchains to configure.
listOf("bukkit", "core", "core-mc").forEach {
    include("worldedit-libs:$it")
    include("worldedit-$it")
}
include("worldedit-libs:core:ap")

include("worldedit-core:doctools")

