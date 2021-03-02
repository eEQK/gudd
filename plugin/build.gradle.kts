plugins {
    `kotlin-jvm`
    `java-gradle-plugin`
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.13.0"
}

dependencies {
    implementation(gradleApi())
    implementation("org.jsoup:jsoup:1.13.1")
}

group = "io.github.eeqk"
version = "1.0.0"

// for `:example` module
publishing {
    repositories {
        maven {
            name = "localPluginRepository"
            url = uri("../local-plugin-repository")
        }
        maven {
            name = "GitHubPackages"
            setUrl("https://maven.pkg.github.com/eEQK/gudd")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

gradlePlugin {
    plugins {
        create("simplePlugin") {
            id = "io.github.eeqk.gudd"
            implementationClass = "gudd.GithubDependencyDownloaderPlugin"
        }
    }
}

