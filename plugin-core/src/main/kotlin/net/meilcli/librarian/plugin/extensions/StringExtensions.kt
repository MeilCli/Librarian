package net.meilcli.librarian.plugin.extensions

fun String.toNullIfEmpty(): String? {
    return if (isEmpty()) null else this
}