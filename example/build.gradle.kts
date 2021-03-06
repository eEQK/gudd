plugins {
    `kotlin-jvm`
    id("io.github.eeqk.gudd") version "1.0.0"
}

repositories {
    jcenter()
}

gudd {
    implementation("cynorkis:cynorkis:1.1.0", "eEQK:cynorkis:630993")
}