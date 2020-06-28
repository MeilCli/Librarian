# Create Own Viewer
You can create own viewer because Librarian just write out JSON

## Json Structure
```json
{
    "title": "Example",
    "description": "Example",
    "notices": [
        {
            "name": "Example",
            "author": "Example human",
            "url": "https://google.com",
            "description": "Description is optional, will be null",
            "resources": [
                {
                    "artifacts": [
                        "com.example:example"
                    ],
                    "licenses": [
                        {
                            "name": "Example license",
                            "url": "https://google.com"
                        }
                    ]
                }
            ]
        }
    ]
}
```

Representing the type information results in the following Kotlin code: 
```kotlin
data class Notices(
    val title: String,
    val description: String?,
    val notices: List<Notice>
)
data class Notice(
    val name: String,
    val author: String,
    val url: String,
    val description: String?,
    val resources: List<NoticeResource>
)
data class NoticeResource(
    val artifacts: List<String>,
    val licenses: List<NoticeLicense>
)
data class NoticeLicense(
    val name: String,
    val url: String
)
```

## Json file path
default out put path is  `/${librarian.dataFolderName}/${librarian.pages.jsonFileName}`, and will write by UTF-8. You can also  use `librarian.pages.jsonAdditionalOutputPath` to write to ther path specified by it

## Attention
Note that you can use JSON to display it however you like, but you need to link the URL. To meet license obligation, must be that User can open URL