plugins {
    kotlin("jvm") version "1.6.21"
    java
    `maven-publish`
}

group = "com.dreamstory.quest"
version = "1.0.0"

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.purpurmc.org/snapshots")
    maven("https://betonquest.org/nexus/repository/betonquest/"){
        content {
            includeGroup("org.betonquest")
        }
        metadataSources {
            artifact()
        }
    }
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.citizensnpcs.co/")

}

dependencies {
    compileOnly("org.purpurmc.purpur","purpur-api","1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc","spigot","1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains.kotlinx", "kotlinx-coroutines-core","1.6.0")

    compileOnly("com.dreamstory.library:DSLibrary:1.0.0")
    compileOnly("com.dreamstory.ability:DSAbility:1.0.0")
    compileOnly("com.dreamstory.crafting:DSCrafting:1.0.0")
    compileOnly("org.betonquest","betonquest","2.0.0-SNAPSHOT")
    compileOnly("io.lumine:Mythic-Dist:5.0.3")
    compileOnly("com.comphenix.protocol","ProtocolLib","4.8.0")
    compileOnly("net.citizensnpcs","citizens-main","2.0.29-SNAPSHOT")
}

