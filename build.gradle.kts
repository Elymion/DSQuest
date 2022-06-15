plugins {
    kotlin("jvm") version "1.6.0"
    java
    `maven-publish`
}

group = "com.dreamstory.quest"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://betonquest.org/nexus/repository/betonquest/"){
        content {
            includeGroup("org.betonquest")
        }
        metadataSources {
            artifact()
        }
    }
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.dreamstory.library:DSLibrary:1.0.0")
    compileOnly("com.dreamstory.ability:DSAbility:1.0.0")
    compileOnly("com.dreamstory.crafting:DSCrafting:1.0.0")
    compileOnly("org.betonquest","betonquest","2.0.0-SNAPSHOT")
    compileOnly("io.lumine:Mythic-Dist:5.0.3")
}

