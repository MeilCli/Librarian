# Librarian
![CI](https://github.com/MeilCli/Librarian/workflows/CI/badge.svg) [ ![Download](https://api.bintray.com/packages/meilcli/librarian/plugin-core/images/download.svg) ](https://bintray.com/meilcli/librarian/plugin-core/_latestVersion) [English](README.md)

Librarianはgradle moduleで使用しているライブラリーの通知を作ります

Librarianは以下の点に焦点を当てています:
- 同一ライブラリーのアーティファクトをグループとして集計
- 正確にライブラリーを集計する
- 予め用意されたプリセット情報による可能な限りの自動化
- コンフィグレーションをページとして集計し、複数の通知ファイルの作成

## 目次
- [必須](README-ja.md#必須)
  - [Gradleのバージョンアップ](README-ja.md#Gradleのバージョンアップ)
- [はじめに](README-ja.md#はじめに)
  - [インストール](README-ja.md#インストール)
  - [タスク](README-ja.md#タスク)
  - [構成](README-ja.md#構成)
  - [通知ページの生成](README-ja.md#通知ページの生成)
- [Androidビューワー](README-ja.md#Androidビューワー)
  - [ui-activityの導入](README-ja.md#ui-activityの導入)
  - [Jsonシリアライザー](README-ja.md#Jsonシリアライザー)
  - [サンプル](README-ja.md#サンプル)
  - [その他](README-ja.md#その他)
- [Viewerの作成](README-ja.md#Viewerの作成)
- [Tips](README-ja.md#tips)
  - [page configurationのメカニズムについて](README-ja.md#page-configurationのメカニズムについて)
  - [Android dynamic feature moduleの依存を通知に追加する](README-ja.md#android-dynamic-feature-moduleの依存を通知に追加する)
  - [Librarianが情報を推測できなくて通知を自動生成できない](README-ja.md#Librarianが情報を推測できなくて通知を自動生成できない)
  - [イメージリソースライセンス通知を追加する](README-ja.md#イメージリソースライセンス通知を追加する)
  - [pomファイルを解決できない](README.md#pomファイルを解決できない)
- [GitHub Actions](README-ja.md#github-actions)
  - [Auto Generate Notice Page and Create Pull Request](README-ja.md#auto-generate-notice-page-and-create-pull-request)
- [Contributing](README.md#Contributing)
- [License](README-ja.md#license)
  - [Using Libraries](README-ja.md#using-libraries)

## 必須
- Gradle 5.5またはそれ以上
- (Android開発の場合) version3.5以上のAndroid StudioとAndroid Gradle Plugin

### Gradleのバージョンアップ
1. プロジェクトのフォルダーを開く
1. `gradle/wrapper/gradle-wrapper.properties`に移動
1. Gradleのバージョンを挙げます
   - たとえばこのように書き換えます:`distributionUrl=https\://services.gradle.org/distributions/gradle-5.5.1-all.zip`

## はじめに
### インストール
LibrarianはGitHub PackagesとBintrayに公開しているのでどちらのMaven Repositoryを参照するか選んでください

Bintrayの場合:
```groovy
buildscript {
    repositories {
        maven { url "https://dl.bintray.com/meilcli/librarian" }
    }
}
```

GitHub Packagesの場合:
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

そしてclasspathを追加してください:
```groovy
buildscript {
    dependencies {
        classpath "net.meilcli.librarian:plugin-core:VERSION" // replace VERSION
        classpath "net.meilcli.librarian:plugin-preset:VERSION" // replace VERSION
    }
}
```

そしてprojectにpluginを適用します:
```groovy
apply plugin: 'librarian'
apply plugin: 'librarian-preset'
```

### タスク
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

### 構成
```groovy
librarian {
    dataFolderName = "Library" // String, default value is Library
    depth = "firstLevel" // String, firstLevel or allLevel, default value is firstLevel
    failOnGeneratePageWhenFoundPlaceholder = true // Boolean, default value is true
    failOnOverrideUnMatchedLicense = true // Boolean, default value is true
    failOnTooManyResolvingConfigurationLimit = 1000 // Int, default value is 1000
    useBintray = false // Boolean, default value is false
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
|librarian.dataFolderName|出力するrootフォルダーの名前|
|librarian.depth|依存を検索する深さ、firstLevelはあなたが直接依存したものを探します|
|librarian.failOnGeneratePageWhenFoundPlaceholder|`librarianGeneratePages`でプレースホルダーが見つかったときに失敗します|
|librarian.failOnOverrideUnMatchedLicense|groupのライセンス情報と一致しないオーバーライドを使用としたときに失敗します|
|librarian.failOnTooManyResolvingConfigurationLimit|configurationの解決が多すぎる場合に失敗させる制限値、マルチモジュールプロジェクトは解決数が指数関数的に増えていきます|
|librarian.useBintray|`librarianGenerateBintrayGroups`タスクのときにBintray APIを使ってライブラリー情報を探します、この機能はアルファ版です|
|librarian.ignoreArtifacts|通知で無視するartifact、前方一致です|

### 通知ページの生成
1. Librarianをインストール
1. プロジェクトで構成を書きます、`pages`ブロックを付けてください
   - `librarianShowConfigurations`タスクを使いながら構成を書くといいでしょう
1. `librarian.failOnGeneratePageWhenFoundPlaceholder`に`false`を設定します
1. `librarianGeneratePresetPipeline`タスクを実行
1. もしエラーが出たり完了しなかったら`groups`ブロックを記述して`librarianGenerateGroups`タスクを実行します
1. `librarian.failOnGeneratePageWhenFoundPlaceholder`に`true`を設定します
1. `librarianGeneratePresetPipeline`タスクを実行


## Androidビューワー
LibrarianはAndroidで通知を表示するためのネイティブビューワーを用意しています

- `ui-core`: UIのコア実装であり、NoticesViewとNoticeViewが含まれてます
- `ui-activity`: Activityとして表示するための`ui-core`のラップ実装です
- `ui-fragment`: Fragmentとして表示するための`ui-core`のラップ実装です
- `ui-serializer-**`: 様々なJson Serializer Libraryのための実装です

### ui-activityの導入
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

### Jsonシリアライザー
- KotlinX Serialization: `ui-serializer-kotlin`
- Moshi: `ui-serializer-moshi`
- Gson: `ui-serializer-gson`

### サンプル
- Using `ui-core`: [sample/sample-ui-core](sample/sample-ui-core)
- Using `ui-activity`: [sample/sample-ui-activity](sample/sample-ui-activity)
- Using `ui-fragment`: [sample/sample-ui-fragment](sample/sample-ui-fragment)
- Using Dynamic Feature Module: [sample/sample-dynamic-app](sample/sample-dynamic-app)
- Custom style: [sample/sample-ui-custom](sample/sample-ui-custom)

### その他
- [Create Own Android Serializer](Document/Viewer/CreateOwnAndroidSerializer.md)

## Viewerの作成
See [Create Own Viewer](Document/Viewer/CreateOwnViewer.md)

## Tips
### page configurationのメカニズムについて
Librarianはそれぞれのページにおいて依存を通知するconfigurationsを選ぶことができます

通常ではプロジェクトはこのようなconfigurationsを持っています:
- `implementationDependenciesMetadata`
- `testImplementationDependenciesMetadata`
- `classpath`
- etc..

`dependencies`ブロックの構成でこららのconfigurationsに依存が追加されます。追加された依存は`librarianShowConfigurations`タスクで確認することができます

通知したいconfiguration名を`pages.configurations`ブロックに追加します。通常では`contain`ブロックを使えばいいです

モジュールをネストした場合、configuration名の正確な順序を設定することができ、`librarianShowFilteredDependencyGraph`タスクではそれらの順序まで表示します

example table:
|configurations|contain("releaseRuntimeClasspath", "apiDependenciesMetadata")|exact("releaseRuntimeClasspath", "apiDependenciesMetadata")|
|:----|:--:|:--:|
|releaseRuntimeClasspath => releaseApiDependenciesMetadata|:x:|:x:|
|releaseRuntimeClasspath => releaseRuntimeClasspath|:o:|:x:|
|releaseRuntimeClasspath => apiDependenciesMetadata|:o:|:o:|

- `contain`: `contain`で設定した値にconfigurationsが収まっているか
- `exact`: `exact`で設定した値と同じか

### Android dynamic feature moduleの依存を通知に追加する
Android dynamic feature moduleを使っている場合は通常とは違う依存グラフになります

dynamic feature moduleのconfiguration名は`releaseReverseMetadataValues`(これはBuild Flavorによって異なる)といったものから始まります

see sample: [sample/sample-dynamic-app](sample/sample-dynamic-app)

### Librarianが情報を推測できなくて通知を自動生成できない
ときどきLibrarianはPomファイルから情報を十分に得ることができません

そういったときはLibrary groupsを使って情報を補足することができます。Library groupはもともとはartifactsを集約するための機能ですが、情報をオーバーライドするためにも使用することができるのです

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

もしくは[ここ](https://github.com/MeilCli/Librarian/issues/new/choose)からプリセットをリクエストすることができます

### イメージリソースライセンス通知を追加する
たまに開発者は使用しているイメージリソースなどの通知をしなければなりません。Librarianはmaven artifactとは別に通知を追加することができます

example of Material design icons:
```groovy
librarian {
    pages {
        "example" { // page name
            additionalNotices {
                "Material design icons" { // Notice name
                    artifacts = ["com.google.material:icons"] // set any value
                    author = "Google Inc."
                    url = https://github.com/google/material-design-icons"
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

### pomファイルを解決できない
たまにLibrarianがpomファイルを解決できない時があります。ほとんどの場合でそれはMaven Repository URLがモジュールのbuild.gradleに設定されていないことが原因です

Librarianのタスクではroot直下のbuild.gradleのbuildscriptも集計します。このとき、root直下のbuild.gradleのbuildscriptのみに設定されていると問題が起きます

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
Each notices is located in the [Library](Library) folder
|artifact name|including binary libraries|using when developing|
|:--|:--|:--|
|`plugin-core`|[plugin-core-usings-plugin](Library/plugin-core-usings-plugin)|[plugin-core-usings-development](Library/plugin-core-usings-development)|
|`plugin-preset`|[plugin-preset-usings-plugin](Library/plugin-preset-usings-plugin)|[plugin-preset-usings-development](Library/plugin-preset-usings-development)|
|`ui-core`|[ui-core-usings-library](Library/ui-core-usings-library)|[ui-core-usings-development](Library/ui-core-usings-development)|
|`ui-activity`|[ui-activity-usings-library](Library/ui-activity-usings-library)|[ui-activity-usings-development](Library/ui-activity-usings-development)|
|`ui-fragment`|[ui-fragment-usings-library](Library/ui-fragment-usings-library)|[ui-fragment-usings-development](Library/ui-fragment-usings-development)|
|`ui-serializer-kotlin`|[ui-serializer-kotlin-usings-library](Library/ui-serializer-kotlin-usings-library)|[ui-serializer-kotlin-usings-development](Library/ui-serializer-kotlin-usings-development)|
|`ui-serializer-moshi`|[ui-serializer-moshi-usings-library](Library/ui-serializer-moshi-usings-library)|[ui-serializer-moshi-usings-development](Library/ui-serializer-moshi-usings-development)|
|`ui-serializer-gson`|[ui-serializer-gson-usings-library](Library/ui-serializer-gson-usings-library)|[ui-serializer-gson-usings-development](Library/ui-serializer-gson-usings-development)|
