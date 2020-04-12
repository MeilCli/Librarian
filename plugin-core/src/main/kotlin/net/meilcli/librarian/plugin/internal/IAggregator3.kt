package net.meilcli.librarian.plugin.internal

interface IAggregator3<TSource1, TSource2, TSource3, TResult> {

    fun aggregate(source1: TSource1, source2: TSource2, source3: TSource3): TResult
}