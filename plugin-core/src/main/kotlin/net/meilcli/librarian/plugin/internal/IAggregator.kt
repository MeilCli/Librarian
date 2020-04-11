package net.meilcli.librarian.plugin.internal

interface IAggregator<TSource, TResult> {

    fun aggregate(source: TSource): TResult
}