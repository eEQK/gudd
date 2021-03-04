plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../../local-plugin-repository")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
    implementation("io.github.eeqk.gudd:io.github.eeqk.gudd.gradle.plugin:1.0.0")
}