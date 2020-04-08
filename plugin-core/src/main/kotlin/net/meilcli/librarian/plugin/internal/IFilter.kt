package net.meilcli.librarian.plugin.internal

interface IFilter<TElement> {

    fun filter(source: TElement): TElement
}