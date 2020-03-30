package net.meilcli.librarian.plugin.extensions

import org.gradle.api.Project
import org.gradle.api.Task

const val librarianGroup = "librarian"

inline fun <reified T : Task> Project.createTask(name: String): T {
    val task = tasks.create(name, T::class.java)
    task.group = librarianGroup
    return task
}