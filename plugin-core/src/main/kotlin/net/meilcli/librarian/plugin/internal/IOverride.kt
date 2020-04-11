package net.meilcli.librarian.plugin.internal

interface IOverride<T> {

    fun override(source: T, override: T): T
}