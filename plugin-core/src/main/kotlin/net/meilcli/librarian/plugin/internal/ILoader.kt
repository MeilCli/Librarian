package net.meilcli.librarian.plugin.internal

interface ILoader<TResult> {

    fun load(): TResult
}