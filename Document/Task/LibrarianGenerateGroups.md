# librarianGenerateGroups
## Description
generate pages that wrote at librarian block on build.gradle

## Related Configuration
- `librarian.groups`
  - NamedDomainObject
  - see sample code

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