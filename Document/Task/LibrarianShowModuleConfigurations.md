# librarianShowModuleConfigurations
## Description
output configurations to console

## Related Configuration
- `librarian.depth`
  - String, `firstLevel` or `allLevel`
  - default: `firstLevel`
  - search dependency depth, firstLevel find your directly dependency
- `librarian.ignoreArtifacts`
  - Array of String
  - ignore notice of maven artifact, ignore by prefix match

## Result
Console:
```
> Task :sample:sample-from-maven:librarianShowModuleConfigurations
Configurations
sample-from-maven:_internal_aapt2_binary
sample-from-maven:androidTestImplementationDependenciesMetadata
sample-from-maven:classpath
sample-from-maven:debugAndroidTestCompileClasspath
sample-from-maven:debugAndroidTestImplementationDependenciesMetadata
sample-from-maven:debugAndroidTestRuntimeClasspath
sample-from-maven:debugCompileClasspath
sample-from-maven:debugImplementationDependenciesMetadata
sample-from-maven:debugRuntimeClasspath
sample-from-maven:debugUnitTestCompileClasspath
sample-from-maven:debugUnitTestImplementationDependenciesMetadata
sample-from-maven:debugUnitTestRuntimeClasspath
sample-from-maven:implementationDependenciesMetadata
sample-from-maven:kotlinCompilerClasspath
sample-from-maven:kotlinCompilerPluginClasspath
sample-from-maven:lintClassPath
sample-from-maven:releaseCompileClasspath
sample-from-maven:releaseImplementationDependenciesMetadata
sample-from-maven:releaseRuntimeClasspath
sample-from-maven:releaseUnitTestCompileClasspath
sample-from-maven:releaseUnitTestImplementationDependenciesMetadata
sample-from-maven:releaseUnitTestRuntimeClasspath
sample-from-maven:testImplementationDependenciesMetadata
```