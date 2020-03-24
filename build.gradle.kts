import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "GameTank"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        maven { setUrl("https://jitpack.io") }
    }
}

repositories {
    mavenCentral()

    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation( "com.github.shaunxiao:kotlinGameEngine:v0.0.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}