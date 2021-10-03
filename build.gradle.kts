plugins {
    kotlin("jvm") version "1.5.10"
}

group = "com.github.highright1234"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib"))
}