# Gudd

### _Github dependency downloader for Gradle_

[![License: Unlicense](https://img.shields.io/badge/license-Unlicense-pink.svg)](http://unlicense.org/)
[![Email](https://img.shields.io/badge/Contact--me-Email-orange.svg)](mailto:karol.czeryna@gmail.com)
[![Telegram](https://img.shields.io/badge/Contact--me-Telegram-blue.svg)](https://t.me/karol.nn)

Gudd allows you to seamlessly integrate with packages from Github that aren't in maven central repository.

```kotlin
gudd {
    // points to https://github.com/eEQK/cynorkis/packages/630993?version=1.1.0
    implementation("cynorkis:cynorkis:1.1.0", "eEQK:cynorkis:630993")
}
```

### Gradle setup

_There's an ongoing effort to publish this plugin into gradle plugin repository, so things will be a whole lot easier in the future._

For a working example that you can actually run, see `example` directory.

* `buildSrc/build.gradle.kts`
```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/eEQK/gudd")
        credentials {
            username = System.getenv("GITHUB_USER")
            password = System.getenv("GITHUB_TOKEN") // with read:packages permission
        }
    }

}

dependencies {
    implementation("io.github.eeqk.gudd:io.github.eeqk.gudd.gradle.plugin:1.0.0")
}
```

* `module/build.gradle.kts` (apart from standard jvm configuration)
```kotlin
plugins {
    id("io.github.eeqk.gudd")
}

repositories {
    // required by one of `cynorkis`' dependencies
    jcenter()
}

gudd {
    implementation("cynorkis:cynorkis:1.1.0", "eEQK:cynorkis:630993")
}
```