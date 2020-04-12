package net.meilcli.librarian.plugin.internal

interface IAggregator1<TSource, TResult> {

    fun aggregate(source: TSource): TResult
}