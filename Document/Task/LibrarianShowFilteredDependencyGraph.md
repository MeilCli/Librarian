# librarianShowFilteredDependencyGraph
## Description
output configurations graph with dependency count to console, filtered by `page.configurations`

## Related Configuration
- `librarian.depth`
  - String, `firstLevel` or `allLevel`
  - default: `firstLevel`
  - search dependency depth, firstLevel find your directly dependency
- `librarian.ignoreArtifacts`
  - Array of String
  - ignore notice of maven artifact, ignore by prefix match
- `librarian.failOnTooManyResolvingConfigurationLimit`
  - Int
  - default: `1000`
  - fail limit that resolving configurations too many, multi module project will be increase exponentially
- `librarian.pages.${name}.configurations`
  - search configuration target names

## Result
Console:
```
> Task :sample:sample-dynamic-app:librarianShowFilteredDependencyGraph
DependencyGraph that filtered by page.configurations
implementationDependenciesMetadata has 5 entries
releaseImplementationDependenciesMetadata has 5 entries
releaseReverseMetadataValues has 0 entries
releaseReverseMetadataValues => releaseRuntimeClasspath has 3 entries
releaseReverseMetadataValues => releaseRuntimeClasspath => implementationDependenciesMetadata has 5 entries
releaseReverseMetadataValues => releaseRuntimeClasspath => releaseImplementationDependenciesMetadata has 5 entries
releaseReverseMetadataValues => releaseRuntimeClasspath => releaseReverseMetadataValues has 0 entries
releaseReverseMetadataValues => releaseRuntimeClasspath => releaseRuntimeClasspath has 5 entries
releaseRuntimeClasspath has 5 entries
```