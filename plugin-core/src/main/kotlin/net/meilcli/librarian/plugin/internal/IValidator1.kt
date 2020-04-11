package net.meilcli.librarian.plugin.internal

interface IValidator1<TElement> {

    fun valid(element: TElement): Boolean
}