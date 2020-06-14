# librarianShowFirstDependencies
## Description
output first level dependencies to console

## Related Configuration
- `librarian.ignoreArtifacts`
  - Array of String
  - ignore notice of maven artifact, ignore by prefix match

## Result
Console:
```
> Task :sample:sample-from-maven:librarianShowFirstDependencies
dependencies
androidx.appcompat:appcompat
androidx.test.espresso:espresso-core
androidx.test.ext:junit
com.android.tools.build:aapt2
com.android.tools.build:gradle
com.android.tools.lint:lint-gradle
com.jfrog.bintray.gradle:gradle-bintray-plugin
junit:junit
net.meilcli.librarian:plugin-core
net.meilcli.librarian:plugin-preset
net.meilcli.librarian:ui-activity
net.meilcli.librarian:ui-serializer-kotlin
org.jetbrains.kotlin:kotlin-android-extensions
org.jetbrains.kotlin:kotlin-android-extensions-runtime
org.jetbrains.kotlin:kotlin-compiler-embeddable
org.jetbrains.kotlin:kotlin-gradle-plugin
org.jetbrains.kotlin:kotlin-serialization
org.jetbrains.kotlin:kotlin-stdlib-jdk7
```