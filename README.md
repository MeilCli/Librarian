# Librarian
[ ![Download](https://api.bintray.com/packages/meilcli/librarian/plugin-core/images/download.svg) ](https://bintray.com/meilcli/librarian/plugin-core/_latestVersion)
Librarian is generate notice that library used in gradle module

- Can notice by every configurations
- Can aggregate artifacts by same library
- Separating notice file

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
    }
}
```

And apply plugin your project:
```groovy
apply plugin: 'librarian'
```

### Task
- `librarianGenerateArtifacts`
  - generate artifacts
- `librarianGenerateGroups`
  - generate groups
- `librarianGeneratePages`
  - generate page, must execute after `librarianGenerateArtifacts`
- `librarianShowConfigurations`
  - output configurations that has dependency to console

### Configuration
```groovy
librarian {
    dataFolderName = "Library" // String, default value is Library
    artifactsFolderName = "Artifacts" // String, default value is Artifacts
    groupsFolderName = "Groups" // String, default value is Groups
    depth = "firstLevel" // String, firstLevel or allLevel, default value is firstLevel
    failOnGeneratePageWhenFoundPlaceholder = true // Boolean, default value is true

    pages {
        "plugin-core-usings-plugin" { // page name, must be unique and not same artifactsFolderNAme and groupsFolderName
            title = "Librarian plugin-core's using libraries" // String?, default value is same the name
            description = null // String?, default value is null
            markdown = true // Boolean, default value is null
            markdownFileName = "README.md" // String, default value is README.md
            json = true // Boolean, default value is null
            jsonFileName = "notices.json" // String, default value is notices.json
            configurations = [
                    "implementationDependenciesMetadata"
            ] // Array of String, default is empty list
        }
    }

    groups {
        "Kotlin" { // group name
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
|:--:|:--:|
|librarian.dataFolderName|output root folder name|
|librarian.artifactsFolderName|output result folder name of `librarianGenerateArtifacts`|
|librarian.groupsFolderName|output result folder name of `librarianGenerateGroups`|
|librarian.depth|search dependency depth, firstLevel find your directly dependency|
|librarian.failOnGeneratePageWhenFoundPlaceholder|fail on `librarianGeneratePages` when found placeholder|

### Generate Notice Page
1. install Librarian
1. configure your project, put `pages`
   - `librarianShowConfigurations` task helps when configure your project
1. execute `librarianGenerateArtifacts` task
1. execute `librarianGeneratePages` task
1. if output error or incomplete result, configure your project that put `groups` and execute `librarianGenerateGroups` task
   - then execute `librarianGeneratePages` task

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

## License
MIT License

- using libraries: [see link](Library/plugin-core-usings-plugin)
- using libraries when developing: [see link](Library/plugin-core-usings-development)