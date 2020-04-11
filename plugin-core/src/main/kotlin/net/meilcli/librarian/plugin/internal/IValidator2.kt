package net.meilcli.librarian.plugin.internal

interface IValidator2<TElement1, TElement2> {

    fun valid(element1: TElement1, element2: TElement2): Boolean
}