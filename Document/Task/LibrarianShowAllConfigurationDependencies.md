# librarianShowAllDependencies
## Description
output all level dependencies with configurations to console

## Related Configuration
- `librarian.ignoreArtifacts`
  - Array of String
  - ignore notice of maven artifact, ignore by prefix match

## Result
Console:
```
> Task :sample:sample-from-maven:librarianShowAllConfigurationDependencies
dependencies
androidx.activity:activity: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.annotation:annotation: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.appcompat:appcompat: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.appcompat:appcompat-resources: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.arch.core:core-common: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.arch.core:core-runtime: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.collection:collection: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.core:core: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.cursoradapter:cursoradapter: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.customview:customview: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.databinding:databinding-common: :sample-from-maven:classpath
androidx.databinding:databinding-compiler-common: :sample-from-maven:classpath
androidx.drawerlayout:drawerlayout: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.fragment:fragment: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.interpolator:interpolator: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.lifecycle:lifecycle-common: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.lifecycle:lifecycle-livedata: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.lifecycle:lifecycle-livedata-core: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.lifecycle:lifecycle-runtime: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.lifecycle:lifecycle-viewmodel: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.lifecycle:lifecycle-viewmodel-savedstate: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.loader:loader: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.recyclerview:recyclerview: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.savedstate:savedstate: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.test.espresso:espresso-core: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
androidx.test.espresso:espresso-idling-resource: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
androidx.test.ext:junit: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
androidx.test:core: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
androidx.test:monitor: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
androidx.test:runner: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
androidx.vectordrawable:vectordrawable: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.vectordrawable:vectordrawable-animated: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.versionedparcelable:versionedparcelable: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
androidx.viewpager:viewpager: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
backport-util-concurrent:backport-util-concurrent: :sample-from-maven:classpath
classworlds:classworlds: :sample-from-maven:classpath
com.android.databinding:baseLibrary: :sample-from-maven:classpath
com.android.tools.analytics-library:crash: :sample-from-maven:classpath
com.android.tools.analytics-library:protos: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.analytics-library:shared: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.analytics-library:tracker: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build.jetifier:jetifier-core: :sample-from-maven:classpath
com.android.tools.build.jetifier:jetifier-processor: :sample-from-maven:classpath
com.android.tools.build:aapt2: :sample-from-maven:_internal_aapt2_binary
com.android.tools.build:aapt2-proto: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:aaptcompiler: :sample-from-maven:classpath
com.android.tools.build:apksig: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:apkzlib: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:builder: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:builder-model: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:builder-test-api: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:bundletool: :sample-from-maven:classpath
com.android.tools.build:gradle: :sample-from-maven:classpath
com.android.tools.build:gradle-api: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:manifest-merger: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.build:transform-api: :sample-from-maven:classpath
com.android.tools.ddms:ddmlib: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.external.com-intellij:intellij-core: :sample-from-maven:lintClassPath
com.android.tools.external.com-intellij:kotlin-compiler: :sample-from-maven:lintClassPath
com.android.tools.external.org-jetbrains:uast: :sample-from-maven:lintClassPath
com.android.tools.layoutlib:layoutlib-api: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools.lint:lint: :sample-from-maven:lintClassPath
com.android.tools.lint:lint-api: :sample-from-maven:lintClassPath
com.android.tools.lint:lint-checks: :sample-from-maven:lintClassPath
com.android.tools.lint:lint-gradle: :sample-from-maven:lintClassPath
com.android.tools.lint:lint-gradle-api: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools:annotations: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools:common: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools:dvlib: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools:repository: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools:sdk-common: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android.tools:sdklib: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android:signflinger: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.android:zipflinger: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.github.gmazzo:gradle-buildconfig-plugin: :sample-from-maven:classpath
com.google.auto.value:auto-value-annotations: :sample-from-maven:classpath
com.google.code.findbugs:jsr305: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.code.gson:gson: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.crypto.tink:tink: :sample-from-maven:classpath
com.google.errorprone:error_prone_annotations: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.guava:failureaccess: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.guava:guava: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.guava:listenablefuture: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.j2objc:j2objc-annotations: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.jimfs:jimfs: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.protobuf:protobuf-java: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.google.protobuf:protobuf-java-util: :sample-from-maven:classpath
com.googlecode.json-simple:json-simple: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.googlecode.juniversalchardet:juniversalchardet: :sample-from-maven:classpath
com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter: :sample-from-maven:classpath
com.jfrog.bintray.gradle:gradle-bintray-plugin: :sample-from-maven:classpath
com.squareup.okhttp3:okhttp: :sample-from-maven:classpath
com.squareup.okio:okio: :sample-from-maven:classpath
com.squareup.retrofit2:retrofit: :sample-from-maven:classpath
com.squareup:javapoet: :sample-from-maven:classpath
com.squareup:javawriter: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.squareup:kotlinpoet: :sample-from-maven:classpath
com.sun.activation:javax.activation: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.sun.istack:istack-commons-runtime: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
com.sun.xml.fastinfoset:FastInfoset: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
commons-beanutils:commons-beanutils: :sample-from-maven:classpath
commons-codec:commons-codec: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
commons-collections:commons-collections: :sample-from-maven:classpath
commons-io:commons-io: :sample-from-maven:classpath
commons-lang:commons-lang: :sample-from-maven:classpath
commons-logging:commons-logging: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
de.undercouch:gradle-download-task: :sample-from-maven:classpath
it.unimi.dsi:fastutil: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
javax.activation:javax.activation-api: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
javax.inject:javax.inject: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:classpath
javax.xml.bind:jaxb-api: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
junit:junit: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestImplementationDependenciesMetadata, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestImplementationDependenciesMetadata, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:testImplementationDependenciesMetadata, :sample-from-maven:classpath
nekohtml:nekohtml: :sample-from-maven:classpath
nekohtml:xercesMinimal: :sample-from-maven:classpath
net.devrieze:serialutil-jvm: :sample-from-maven:classpath
net.devrieze:xmlutil-jvm: :sample-from-maven:classpath
net.devrieze:xmlutil-serialization-jvm: :sample-from-maven:classpath
net.meilcli.librarian:plugin-core: :sample-from-maven:classpath
net.meilcli.librarian:plugin-preset: :sample-from-maven:classpath
net.meilcli.librarian:ui-activity: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
net.meilcli.librarian:ui-core: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
net.meilcli.librarian:ui-serializer-kotlin: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
net.sf.ezmorph:ezmorph: :sample-from-maven:classpath
net.sf.jopt-simple:jopt-simple: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
net.sf.json-lib:json-lib: :sample-from-maven:classpath
net.sf.kxml:kxml2: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:classpath
net.sf.proguard:proguard-base: :sample-from-maven:classpath
net.sf.proguard:proguard-gradle: :sample-from-maven:classpath
net.sourceforge.nekohtml:nekohtml: :sample-from-maven:classpath
org.antlr:antlr4: :sample-from-maven:classpath
org.apache.ant:ant: :sample-from-maven:classpath
org.apache.ant:ant-launcher: :sample-from-maven:classpath
org.apache.commons:commons-compress: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.apache.commons:commons-lang3: :sample-from-maven:classpath
org.apache.httpcomponents:httpclient: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.apache.httpcomponents:httpcore: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.apache.httpcomponents:httpmime: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.apache.maven.wagon:wagon-file: :sample-from-maven:classpath
org.apache.maven.wagon:wagon-http-lightweight: :sample-from-maven:classpath
org.apache.maven.wagon:wagon-http-shared: :sample-from-maven:classpath
org.apache.maven.wagon:wagon-provider-api: :sample-from-maven:classpath
org.apache.maven:maven-ant-tasks: :sample-from-maven:classpath
org.apache.maven:maven-artifact: :sample-from-maven:classpath
org.apache.maven:maven-artifact-manager: :sample-from-maven:classpath
org.apache.maven:maven-error-diagnostics: :sample-from-maven:classpath
org.apache.maven:maven-model: :sample-from-maven:classpath
org.apache.maven:maven-plugin-registry: :sample-from-maven:classpath
org.apache.maven:maven-profile: :sample-from-maven:classpath
org.apache.maven:maven-project: :sample-from-maven:classpath
org.apache.maven:maven-repository-metadata: :sample-from-maven:classpath
org.apache.maven:maven-settings: :sample-from-maven:classpath
org.bouncycastle:bcpkix-jdk15on: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.bouncycastle:bcprov-jdk15on: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.checkerframework:checker-qual: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.codehaus.groovy.modules.http-builder:http-builder: :sample-from-maven:classpath
org.codehaus.groovy:groovy-all: :sample-from-maven:lintClassPath
org.codehaus.mojo:animal-sniffer-annotations: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.codehaus.plexus:plexus-container-default: :sample-from-maven:classpath
org.codehaus.plexus:plexus-interpolation: :sample-from-maven:classpath
org.codehaus.plexus:plexus-utils: :sample-from-maven:classpath
org.glassfish.jaxb:jaxb-runtime: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.glassfish.jaxb:txw2: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.hamcrest:hamcrest-core: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestImplementationDependenciesMetadata, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestImplementationDependenciesMetadata, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:testImplementationDependenciesMetadata
org.hamcrest:hamcrest-integration: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
org.hamcrest:hamcrest-library: :sample-from-maven:androidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestImplementationDependenciesMetadata, :sample-from-maven:debugAndroidTestRuntimeClasspath
org.jdom:jdom2: :sample-from-maven:classpath
org.jetbrains.intellij.deps:trove4j: :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-android-extensions: :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-android-extensions-runtime: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath
org.jetbrains.kotlin:kotlin-annotation-processing-gradle: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-build-common: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-compiler-embeddable: :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-compiler-runner: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-daemon-client: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-daemon-embeddable: :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-gradle-plugin: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-gradle-plugin-api: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-gradle-plugin-model: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-native-utils: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-reflect: :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-script-runtime: :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-scripting-common: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-scripting-compiler-impl-embeddable: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-scripting-jvm: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-serialization: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-stdlib: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-stdlib-common: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-stdlib-jdk7: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:lintClassPath, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-stdlib-jdk8: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-util-io: :sample-from-maven:classpath
org.jetbrains.kotlin:kotlin-util-klib: :sample-from-maven:classpath
org.jetbrains.kotlinx:kotlinx-coroutines-core: :sample-from-maven:classpath
org.jetbrains.kotlinx:kotlinx-serialization-runtime: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:classpath
org.jetbrains.kotlinx:kotlinx-serialization-runtime-common: :sample-from-maven:classpath
org.jetbrains.trove4j:trove4j: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.jetbrains:annotations: :sample-from-maven:debugAndroidTestCompileClasspath, :sample-from-maven:debugAndroidTestRuntimeClasspath, :sample-from-maven:debugCompileClasspath, :sample-from-maven:debugImplementationDependenciesMetadata, :sample-from-maven:debugRuntimeClasspath, :sample-from-maven:debugUnitTestCompileClasspath, :sample-from-maven:debugUnitTestRuntimeClasspath, :sample-from-maven:implementationDependenciesMetadata, :sample-from-maven:kotlinCompilerClasspath, :sample-from-maven:kotlinCompilerPluginClasspath, :sample-from-maven:lintClassPath, :sample-from-maven:releaseCompileClasspath, :sample-from-maven:releaseImplementationDependenciesMetadata, :sample-from-maven:releaseRuntimeClasspath, :sample-from-maven:releaseUnitTestCompileClasspath, :sample-from-maven:releaseUnitTestRuntimeClasspath, :sample-from-maven:classpath
org.json:json: :sample-from-maven:classpath
org.jvnet.staxex:stax-ex: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.ow2.asm:asm: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.ow2.asm:asm-analysis: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.ow2.asm:asm-commons: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.ow2.asm:asm-tree: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
org.ow2.asm:asm-util: :sample-from-maven:lintClassPath, :sample-from-maven:classpath
xerces:xercesImpl: :sample-from-maven:classpath
xml-resolver:xml-resolver: :sample-from-maven:classpath
```