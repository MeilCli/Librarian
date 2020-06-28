# Customize Android Viewer
Currently, Librarian Preset Android Viewer is replacable layout file, but the number of customizable parts is minimal. So, if you want to make a big change, you will most likely to reimplemented

## Change layout, view or text appearance
[NoticesView](../../ui-core/src/main/kotlin/net/meilcli/librarian/views/NoticesView.kt) and [NoticeView](../../ui-core/src/main/kotlin/net/meilcli/librarian/views/NoticeView.kt) can change layout using [NoticesStyle](../../ui-core/src/main/kotlin/net/meilcli/librarian/NoticesStyle.kt) and [NoticeStyle](../../ui-core/src/main/kotlin/net/meilcli/librarian/NoticeStyle.kt)

Pay attention when change `LayoutRes` with `IdRes`, those values are set