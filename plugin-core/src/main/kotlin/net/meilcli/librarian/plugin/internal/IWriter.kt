package net.meilcli.librarian.plugin.internal

interface IWriter<in TSource> {

    fun write(source: TSource)
}