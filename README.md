# Librarian
![CI](https://github.com/MeilCli/Librarian/workflows/CI/badge.svg) [ ![Download](https://api.bintray.com/packages/meilcli/librarian/plugin-core/images/download.svg) ](https://bintray.com/meilcli/librarian/plugin-core/_latestVersion) [日本語](README-ja.md)

Librarian is generate notice that library used in gradle module

Librarian can:
- notify by every configurations
- aggregate artifacts by same library
- reference preset library information
- generate markdown and json
- separating notice file
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
  - [About the mechanism of page configuration](README.md#about-the-mechanism-of-page-configuration)
  - [Add android dynamic feature module dependency notices](README.md#add-android-dynamic-feature-module-dependency-notices)
  - [Cannot auto generate because Librarian dose not infer some information](README.md#cannot-auto-generate-because-librarian-dose-not-infer-some-information)
  - [Add image resource license notice](README.md#add-image-resource-license-notice)
  - [Cannot resolve pom file](README.md#cannot-resolve-pom-file)
- [GitHub Actions](README.md#github-actions)
  - [Auto Generate Notice Page and Create Pull Request](README.md#auto-generate-notice-page-and-create-pull-request)
- [Contributing](README.md#Contributing)
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
  - [librarianGenerateArtifacts](Document/Task/LibrarianGenerateArtifacts.md)
  - [librarianGenerateGroups](Document/Task/LibrarianGenerateGroups.md)
  - [librarianGenerateBintrayGroups](Document/Task/LibrarianGenerateBintrayGroups.md)
  - [librarianGeneratePages](Document/Task/LibrarianGeneratePages.md)
  - [librarianGeneratePipeline](Document/Task/LibrarianGeneratePipeline.md)
  - [librarianShowConfigurations](Document/Task/LibrarianShowConfigurations.md)
  - [librarianShowModuleConfigurations](Document/Task/LibrarianShowModuleConfigurations.md)
  - [librarianShowFirstDependencies](Document/Task/LibrarianShowFirstDependencies.md)
  - [librarianShowAllDependencies](Document/Task/LibrarianShowAllDependencies.md)
  - [librarianShowFirstConfigurationDependencies](Document/Task/LibrarianShowFirstConfigurationDependencies.md)
  - [librarianShowAllConfigurationDependencies](Document/Task/LibrarianShowAllConfigurationDependencies.md)
  - [librarianShowFilteredDependencyGraph](Document/Task/LibrarianShowFilteredDependencyGraph.md)
- librarian preset plugin
  - [librarianGeneratePresetGroups](Document/Task/LibrarianGeneratePresetGroups.md)
  - [librarianGeneratePresetPipeline](Document/Task/LibrarianGeneratePresetPipeline.md)

### Configuration
```groovy
librarian {
    dataFolderName = "Library" // String, default value is Library
    depth = "firstLevel" // String, firstLevel or allLevel, default value is firstLevel
    failOnGeneratePageWhenFoundPlaceholder = true // Boolean, default value is true
    failOnOverrideUnMatchedLicense = true // Boolean, default value is true
    failOnTooManyResolvingConfigurationLimit = 1000 // Int, default value is 1000
    useBintray = true // Boolean, default value is false
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
                    artifacts = ["text:com"] // Array of String, default is empty
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
|librarian.failOnTooManyResolvingConfigurationLimit|fail limit that resolving configurations too many, multi module project will be increase exponentially|
|librarian.useBintray|if true, actually use Bintray api at `librarianGenerateBintrayGroups` task, this feature is alpha|
|librarian.ignoreArtifacts|ignore notice of maven artifact, ignore by prefix match|

### Generate Notice Page
1. install Librarian
1. configure your project, put `pages`
   - `librarianShowConfigurations` task helps when configure your project
1. set `false` to `librarian.failOnGeneratePageWhenFoundPlaceholder`
1. execute `librarianGeneratePresetPipeline` task
1. if console output error or incomplete result, configure your project that put `groups`
1. set `true` to `librarian.failOnGeneratePageWhenFoundPlaceholder`
1. execute `librarianGeneratePresetPipeline` task

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
- Custom style: [sample/sample-ui-custom](sample/sample-ui-custom)

## Tips
### About the mechanism of page configuration
Librarian can select configurations that including dependencies to notify on every page

in normal case, project has some configurations such as:
- `implementationDependenciesMetadata`
- `testImplementationDependenciesMetadata`
- `classpath`
- etc..

the `dependencies` block configuration adds dependencies to these configurations. you can see configurations that has dependencies by `librarianShowConfigurations` task

add configuration names that want to notify at page to `pages.configurations` block. in normal case, you can use `contain` block

in nested module case, you can set exact configuration name order, and `librarianShowFilteredDependencyGraph` task show configuration name order

example table:
|configurations|contain("releaseRuntimeClasspath", "apiDependenciesMetadata")|exact("releaseRuntimeClasspath", "apiDependenciesMetadata")|
|:----|:--:|:--:|
|releaseRuntimeClasspath => releaseApiDependenciesMetadata|:x:|:x:|
|releaseRuntimeClasspath => releaseRuntimeClasspath|:o:|:x:|
|releaseRuntimeClasspath => apiDependenciesMetadata|:o:|:o:|

- `contain`: whether configuration names is contained in value
- `exact`: whether configuration names is equaled to value

### Add android dynamic feature module dependency notices
in the case of android dynamic feature module, dependency graph is different from normal case

dynamic feature module configuration names is the starting point about `releaseReverseMetadataValues`(different every build flavor)

see sample: [sample/sample-dynamic-app](sample/sample-dynamic-app)

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

### Add image resource license notice
Sometimes, developers must notify image resource licence or else. Librarian can add notice apart from maven artifact

example of Material design icons:
```groovy
librarian {
    pages {
        "example" { // page name
            additionalNotices {
                "Material design icons" { // Notice name
                    artifacts = []
                    author = "Google Inc."
                    url = "https://github.com/google/material-design-icons"
                    description = "Material Design icons by Google" // optional
                    licenses {
                        "Apache License 2.0" {
                            url = "https://github.com/google/material-design-icons/blob/master/LICENSE"
                        }
                    }
                }
            }
        }
    }
}
```

### Cannot resolve pom file
Sometimes, Librarian cannot resolve pom file. In most cases it's because Maven Repository URL is not set to module's build.gradle.

Librarian task aggregate root build.gradle's buildscript. in time, if it is only set to root build.gradle's buildscript, problems will occur.

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

## Contributing
see [Contributing.md](.github/CONTRIBUTING.md). if you think to report issue, select [Issue Template](https://github.com/MeilCli/Librarian/issues/new/choose)

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
