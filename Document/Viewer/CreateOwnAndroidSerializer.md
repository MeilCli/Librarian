# Create Own Android Serializer
You can implement your own Json Serializer that Librarian does not have

## Overview
- You cam implement own Json Serializer using interfaces defined at `ui-core`
- If you do not want to depend on `ui-core`, see [Create Own Viewer](CreateOwnViewer.md)

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

## Implement interface
- [INoticesReader](../../ui-core/src/main/kotlin/net/meilcli/librarian/INoticesReader.kt): This interface should be Serializable structure
- [INotices](../../ui-core/src/main/kotlin/net/meilcli/librarian/INotices.kt), [INotice](../../ui-core/src/main/kotlin/net/meilcli/librarian/INotice.kt), [INoticeResource](../../ui-core/src/main/kotlin/net/meilcli/librarian/INoticeResource.kt), [ILicense](../../ui-core/src/main/kotlin/net/meilcli/librarian/ILicense.kt): Pay attention to proguard when implement

## Contributing
If you want to make your own implementation or you made your own implementation, it is chance to contribute. Librarian welcomes contributions on Issues or Pull Requests