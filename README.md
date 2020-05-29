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
- [Android Viewer](README.md#android-viewer)
  - [Getting started of ui-activity](README.md#getting-started-of-ui-activity)
  - [Serializers](README.md#serializers)
  - [Samples](README.md#samples)
- [Tips](README.md#tips)
  - [Cannot auto generate because Librarian dose not infer some information](README.md#cannot-auto-generate-because-librarian-dose-not-infer-some-information)
- [GitHub Actions](README.md#github-actions)
  - [Auto Generate Notice Page and Create Pull Request](README.md#auto-generate-notice-page-and-create-pull-request)
- [License](README.md#license)
  - [Using Libraries](README.md#using-libraries)
  
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
  - `librarianGenerateBintrayGroups`
    - generate groups using by Bintray package information
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
    failOnOverrideUnMatchedLicense = true // Boolean, default value is true
    useBintray = true // Boolean, default value is true
    additionalModules = [] // Array of String
    ignoreArtifacts = [] // Array of String

    pages {
        "plugin-core-usings-plugin" { // page name, must be unique
            title = "Librarian plugin-core's using libraries" // String?, default value is same the name
            description = null // String?, default value is null
            markdown = true // Boolean, default value is null
            markdownFileName = "README.md" // String, default value is README.md
            json = true // Boolean, default value is null
            jsonFileName = "notices.json" // String, default value is notices.json
            jsonAdditionalOutputPath = null // File, default is null
            configurations {
                exact {
                    value = [""] // Array of String
                }
                contain {
                    value = [""]
                }
                exact { value = [""] } // exact and contain can be multiple
            }
            additionalNotices {
                "AdditionalNotice" { // Notice name
                    artifacts = ["text:com"] // Array of String, default is empty, must set value
                    author = "Tester" // String, default is empty, must set value
                    url = "https://google.com" // String, default is empty, must set value
                    description = "" // String?, default value is null
                    licenses {
                        "MIT" { // License name, must set value
                            url = "https://google.com" // String, default is empty, must set value
                        }
                    }
                }
            }
        }
    }

    groups {
        "Kotlin" { // group name, must be unique
            artifacts = [
                    "org.jetbrains.kotlin:kotlin-gradle-plugin",
                    "org.jetbrains.kotlin:kotlin-serialization",
                    "org.jetbrains.kotlin:kotlin-stdlib-jdk7"
            ] // Array of String, default is empty list
            author = null // String?, default value is null
            url = null // String?, default value is null
            description = null // String?, default value is null
            licenseName = null // String?, default value is null
            licenseUrl = null // String?, default value is null
        }
    }
}
```

|path|summary|
|:--|:--|
|librarian.dataFolderName|output root folder name|
|librarian.depth|search dependency depth, firstLevel find your directly dependency|
|librarian.failOnGeneratePageWhenFoundPlaceholder|fail on `librarianGeneratePages` when found placeholder|
|librarian.failOnOverrideUnMatchedLicense|fail on override un matched license by group|
|librarian.useBintray|if true, actually use Bintray api at `librarianGenerateBintrayGroups` task`|
|librarian.additionalModules|additional resolve modules, use when like android dynamic feature module|
|librarian.ignoreArtifacts|ignore notice of maven artifact, ignore by prefix match|

### Generate Notice Page
1. install Librarian
1. configure your project, put `pages`
   - `librarianShowConfigurations` task helps when configure your project
1. execute `librarianGenerateArtifacts` task
1. execute `librarianGeneratePresetGroups` task if using `librarian-preset`
1. execute `librarianGenerateBintrayGroups` task if using Bintray package information
1. execute `librarianGeneratePages` task
1. if output error or incomplete result, configure your project that put `groups` and execute `librarianGenerateGroups` task
   - then execute `librarianGeneratePages` task

if you want generate notice page by one task, execute `librarianGeneratePipeline` or `librarianGeneratePresetPipeline`

## Android Viewer
Librarian is prepared Android Viewer library

- `ui-core`: core of UI, including some interface, NoticesView and NoticeView
- `ui-activity`: wrap of core, for easily usage of Activity
- `ui-fragment`: wrap of core, for easily usage of Fragment
- `ui-serializer-**`: serializer implementation of some Json Serializer Library

### Getting started of ui-activity
install UI Library:
```groovy
dependencies {
    implementation "net.meilcli.librarian:ui-activity:VERSION" // replace VERSION
    implementation "net.meilcli.librarian:ui-serializer-kotlin:VERSION" // replace VERSION and serializer
}
```

configure Librarian:
```groovy
apply plugin: 'librarian'
apply plugin: 'librarian-preset'

librarian {
    pages {
        "sample-from-maven" {
            title = "Using Libraries"
            description = "sample-from-maven is using this libraries."
            configurations {
                contain {
                    value = [
                        "implementationDependenciesMetadata",
                        "releaseImplementationDependenciesMetadata"
                    ]
                }
            }
            jsonAdditionalOutputPath = file("src/main/assets/notices.json")
        }
    }
}
```

register activity:
```xml
<application>
    <!-- Replace your NoActionBar style -->
    <activity
        android:name="net.meilcli.librarian.activities.NoticesActivity"
        android:theme="@style/AppTheme.NoActionBar" />
    <activity
        android:name="net.meilcli.librarian.activities.NoticeActivity"
        android:theme="@style/AppTheme.NoActionBar" />
</application>
```

register listener:
```kotlin
button.setOnClickListener {
    startActivity(NoticesActivity.createIntent(this, NoticesReader(), "notices.json"))
}
```

### Serializers
- KotlinX Serialization: `ui-serializer-kotlin`
- Moshi: `ui-serializer-moshi`
- Gson: `ui-serializer-gson`

### Samples
- Using `ui-core`: [sample/sample-ui-core](sample/sample-ui-core)
- Using `ui-activity`: [sample/sample-ui-activity](sample/sample-ui-activity)
- Using `ui-fragment`: [sample/sample-ui-fragment](sample/sample-ui-fragment)
- Using Dynamic Feature Module: [sample/sample-dynamic-app](sample/sample-dynamic-app)

## Tips
### Cannot auto generate because Librarian dose not infer some information
Sometimes, Librarian dose not infer Library information when not enough pom file

You can compensate information by using Library groups. Library groups is prepared originally for aggregation artifacts, but can use as to override information your hand

example:
```groovy
librarian {
    groups {
        "Kotlin" { // group name, must be unique
            artifacts = [
                    "org.jetbrains.kotlin:kotlin-gradle-plugin",
                    "org.jetbrains.kotlin:kotlin-serialization",
                    "org.jetbrains.kotlin:kotlin-stdlib-jdk7"
            ] // Array of String, default is empty list
            author = null // String?, default value is null
            url = null // String?, default value is null
            description = null // String?, default value is null
            licenseName = null // String?, default value is null
            licenseUrl = null // String?, default value is null
        }
    }
}
```

Or, can request preset [here](https://github.com/MeilCli/Librarian/issues/new/choose)

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
Librarian is MIT License

### Using Libraries
including binary:
- `plugin-core`: [see link](Library/plugin-core-usings-plugin)
- `plugin-preset`: [see link](Library/plugin-preset-usings-plugin)
- `ui-core`: [see link](Library/ui-core-usings-library)
- `ui-activity`: [see link](Library/ui-activity-usings-library)
- `ui-fragment`: [see link](Library/ui-fragment-usings-library)
- `ui-serializer-kotlin`: [see link](Library/ui-serializer-kotlin-usings-library)
- `ui-serializer-moshi`: [see link](Library/ui-serializer-moshi-usings-library)
- `ui-serializer-gson`: [see link](Library/ui-serializer-gson-usings-library)

when developing
- `plugin-core`: [see link](Library/plugin-core-usings-development)
- `plugin-preset`: [see link](Library/plugin-preset-usings-development)
- `ui-core`: [see link](Library/ui-core-usings-development)
- `ui-activity`: [see link](Library/ui-activity-usings-development)
- `ui-fragment`: [see link](Library/ui-fragment-usings-development)
- `ui-serializer-kotlin`: [see link](Library/ui-serializer-kotlin-usings-development)
- `ui-serializer-moshi`: [see link](Library/ui-serializer-moshi-usings-development)
- `ui-serializer-gson`: [see link](Library/ui-serializer-gson-usings-development)
