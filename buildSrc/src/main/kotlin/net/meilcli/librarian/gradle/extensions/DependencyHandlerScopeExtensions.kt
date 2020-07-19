package net.meilcli.librarian.gradle.extensions

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandlerScope.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandlerScope.testRuntimeOnly(dependency: String) {
    add("testRuntimeOnly", dependency)
}

fun DependencyHandlerScope.androidTestImplementation(dependency: String) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandlerScope.detektPlugins(dependency: String) {
    add("detektPlugins", dependency)
}