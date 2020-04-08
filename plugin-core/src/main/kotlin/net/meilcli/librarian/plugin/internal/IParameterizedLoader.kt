package net.meilcli.librarian.plugin.internal

interface IParameterizedLoader<TParameter, TResult> {

    fun load(parameter: TParameter): TResult
}