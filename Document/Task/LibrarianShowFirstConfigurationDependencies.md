# librarianShowFirstDependencies
## Description
output first level dependencies with configurations to console

## Related Configuration
- `librarian.ignoreArtifacts`
  - Array of String
  - ignore notice of maven artifact, ignore by prefix match

## Result
Console:
```
> Task :sample:sample-from-maven:librarianShowFirstConfigurationDependencies
dependencies
androidx.appcompat:appcompat: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.test.espresso:espresso-core: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
androidx.test.ext:junit: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
com.android.tools.build:aapt2: :sample-from-maven:_internal_aapt2_binary
com.android.tools.build:gradle: :sample-from-maven:classpath
com.android.tools.lint:lint-gradle: :sample-from-maven:lintClassPath
com.github.gmazzo:gradle-buildconfig-plugin: :sample-from-maven:classpath
com.jfrog.bintray.gradle:gradle-bintray-plugin: :sample-from-maven:classpath
junit:junit: :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestImplementationDependenciesMetadata, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestImplementationDependenciesMetadata, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:testImplementationDependenciesMetadata
net.meilcli.librarian:plugin-core: :sample-from-maven:classpath
net.meilcli.librarian:plugin-preset: :sample-from-maven:classpath
net.meilcli.librarian:ui-activity: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
net.meilcli.librarian:ui-serializer-kotlin: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
org.jetbrains.kotlin:kotlin-android-extensions: :sample-from-maven:kotlinCompilerPluginClasspath
org.jetbrains.kotlin:kotlin-android-extensions-runtime: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
org.jetbrains.kotlin:kotlin-compiler-embeddable: :sample-from-maven:kotlinCompilerClasspath
org.jetbrains.kotlin:kotlin-gradle-plugin: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-serialization: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-stdlib-jdk7: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
```