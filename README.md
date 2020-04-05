# Librarian
![CI](https://github.com/MeilCli/Librarian/workflows/CI/badge.svg) [ ![Download](https://api.bintray.com/packages/meilcli/librarian/plugin-core/images/download.svg) ](https://bintray.com/meilcli/librarian/plugin-core/_latestVersion)  
Librarian is generate notice that library used in gradle module

- Can notice by every configurations
- Can aggregate artifacts by same library
- Preset famous library groups
- Separating notice file
- Android native viewer

## Table of Contents
- [Required](README.md#Required)
  - [Upgrade Gradle version](README.md#upgrade-gradle-version)
- [Getting started](README.md#getting-started)
  - [Install](README.md#install)
  - [Task](README.md#task)
  - [Configuration](README.md#configuration)
  - [Generate Notice Page](README.md#generate-notice-page)
- [GitHub Actions](README.md#github-actions)
  - [Auto Generate Notice Page and Create Pull Request](README.md#auto-generate-notice-page-and-create-pull-request)
- [License](README.md#license)
  
## Required
- Gradle 5.5 or over
- (if you develop Android app) Android Studio and Android Gradle Plugin 3.5 or over

### Upgrade Gradle version
1. open project folder
1. move `gradle/wrapper/gradle-wrapper.properties`
1. upgrade gradle version
   - for example: `distributionUrl=https\://services.gradle.org/distributions/gradle-5.5.1-all.zip`

## Getting started
### Install
Librarian is published on GitHub Packages and Bintray. So you choice maven repository.

using Bintray:
```groovy
buildscript {
    repositories {
        maven { url "https://dl.bintray.com/meilcli/librarian" }
    }
}
```

using GitHub Packages:
```groovy
buildscript {
    repositories {
        maven {
            url "https://maven.pkg.github.com/MeilCli/Librarian"
            credentials {
                username System.getenv("GITHUB_USER")
                password System.getenv("GITHUB_TOKEN") // token has permission of read:packages
            }
        }
    }
}
```

And set classpath:
```groovy
buildscript {
    dependencies {
        classpath "net.meilcli.librarian:plugin-core:VERSION" // replace VERSION
        classpath "net.meilcli.librarian:plugin-preset:VERSION" // replace VERSION
    }
}
```

And apply plugin your project:
```groovy
apply plugin: 'librarian'
apply plugin: 'librarian-preset'
```

### Task
- librarian plugin
  - `librarianGenerateArtifacts`
    - generate artifacts
  - `librarianGenerateGroups`
    - generate groups
  - `librarianGeneratePages`
    - generate page, must execute after `librarianGenerateArtifacts`
  - `librarianGeneratePipeline`
    - execute tasks: `librarianGenerateArtifacts`, `librarianGenerateGroups` and `librarianGeneratePages`
  - `librarianShowConfigurations`
    - output configurations that has dependency to console
- librarian preset plugin
  - `librarianGeneratePresetGroups`
    - generate preset groups, recommend execute before `librarianGenerateGroups`
  - `librarianGeneratePresetPipeline`
    - execute tasks: `librarianGenerateArtifacts`, `librarianGeneratePresetGroups` ,`librarianGenerateGroups` and `librarianGeneratePages`

### Configuration
```groovy
librarian {
    dataFolderName = "Library" // String, default value is Library
    depth = "firstLevel" // String, firstLevel or allLevel, default value is firstLevel
    failOnGeneratePageWhenFoundPlaceholder = true // Boolean, default value is true

    pages {
        "plugin-core-usings-plugin" { // page name, must be unique
            title = "Librarian plugin-core's using libraries" // String?, default value is same the name
            description = null // String?, default value is null
            markdown = true // Boolean, default value is null
            markdownFileName = "README.md" // String, default value is README.md
            json = true // Boolean, default value is null
            jsonFileName = "notices.json" // String, default value is notices.json
            jsonAdditionalOutputPath = null // File, default is null
            configurations = [
                    "implementationDependenciesMetadata"
            ] // Array of String, default is empty list
        }
    }

    groups {
        "Kotlin" { // group name, must be unique
            artifacts = [
                    "org.jetbrains.kotlin:kotlin-gradle-plugin",
                    "org.jetbrains.kotlin:kotlin-serialization",
                    "org.jetbrains.kotlin:kotlin-stdlib-jdk7"
            ] // Array of String, default is empty list
        }
        author = null // String?, default value is null
        url = null // String?, default value is null
        description = null // String?, default value is null
        licenseName = null // String?, default value is null
        licenseUrl = null // String?, default value is null
    }
}
```

|path|summary|
|:--|:--|
|librarian.dataFolderName|output root folder name|
|librarian.depth|search dependency depth, firstLevel find your directly dependency|
|librarian.failOnGeneratePageWhenFoundPlaceholder|fail on `librarianGeneratePages` when found placeholder|

### Generate Notice Page
1. install Librarian
1. configure your project, put `pages`
   - `librarianShowConfigurations` task helps when configure your project
1. execute `librarianGenerateArtifacts` task
1. execute `librarianGeneratePresetGroups` task if using `librarian-preset`
1. execute `librarianGeneratePages` task
1. if output error or incomplete result, configure your project that put `groups` and execute `librarianGenerateGroups` task
   - then execute `librarianGeneratePages` task

if you want generate notice page by one task, execute `librarianGeneratePipeline` or `librarianGeneratePresetPipeline`

## GitHub Actions
if you use GitHub Actions, recommend use GitHub Packages when CI Build

switch maven repository by environment variables:
```groovy
buildscript {
    repositories {
        def githubUser = System.getenv("GITHUB_USER")
        def githubToken = System.getenv("GITHUB_TOKEN")
        if (githubUser != null && githubToken != null) {
            maven {
                url "https://maven.pkg.github.com/MeilCli/Librarian"
                credentials {
                    username githubUser
                    password githubToken
                }
            }
        } else {
            maven { url "https://dl.bintray.com/meilcli/librarian" }
        }
    }
}
```

set environment your workflow
```yml
jobs:
  build:
    env:
      GITHUB_USER: "github-bot"
      GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

### Auto Generate Notice Page and Create Pull Request
using [peter-evans/create-pull-request](https://github.com/peter-evans/create-pull-request) example:

```yml
name: CI

on:
  push:
    branches:
      - '*'
    tags-ignore:
      - '*'

jobs:
  librarian:
    runs-on: ubuntu-latest
    env:
      GITHUB_USER: "github-bot"
      GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v1
        with:
          java-version: 1.8
      - name: Grant permission
        run: chmod +x gradlew
      - run: ./gradlew librarianGeneratePresetPipeline
      - name: Create Pull Request
        uses: peter-evans/create-pull-request@v2
        with:
          commit-message: "update library notices"
          title: "update library notices"
```

## License
MIT License

- using libraries: [see link](Library/plugin-core-usings-plugin)
- using libraries when developing: [see link](Library/plugin-core-usings-development)
