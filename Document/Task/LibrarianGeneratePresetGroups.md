# librarianGenerateGroups
## Description
generate pages that wrote at librarian block on build.gradle

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
write group json at `{module}/build/librarian/groups/`

```json
{
    "artifacts": [
        "com.example:example"
    ],
    "name": "Example",
    "author": "Example human",
    "url": "https://google.com",
    "description": "Description",
    "licenses": [
        {
            "name": "Example License",
            "url": "https://google.com"
        }
    ]
}
```
Element is optional without artifacts, will be null