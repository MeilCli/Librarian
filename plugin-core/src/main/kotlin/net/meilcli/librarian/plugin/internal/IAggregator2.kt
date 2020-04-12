package net.meilcli.librarian.plugin.internal

interface IAggregator2<TSource1, TSource2, TResult> {

    fun aggregate(source1: TSource1, source2: TSource2): TResult
}