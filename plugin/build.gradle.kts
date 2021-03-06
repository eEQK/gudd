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

publishing {
    repositories {
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
        create("gudd") {
            id = "io.github.eeqk.gudd"
            implementationClass = "gudd.GithubDependencyDownloaderPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/eEQK/gudd"
    vcsUrl = "https://github.com/eEQK/gudd"
    tags = listOf("github", "dependency")
    description = "GitHub dependency downloader"
    (plugins) {
        "gudd" {
            displayName = "GitHub dependency downloader"
        }
    }
}