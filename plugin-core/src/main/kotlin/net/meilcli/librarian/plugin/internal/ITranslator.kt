package net.meilcli.librarian.plugin.internal

interface ITranslator<TSource, TResult> {

    fun translate(source: TSource): TResult
}