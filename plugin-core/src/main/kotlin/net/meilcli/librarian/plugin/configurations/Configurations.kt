package net.meilcli.librarian.plugin.configurations

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

@Suppress("UnstableApiUsage")
open class Configurations @Inject constructor(
    objectFactory: ObjectFactory
) {

    val value = objectFactory.domainObjectSet(IConfiguration::class.java)

    fun exact(action: Action<in ExactConfiguration>) {
        val configuration = ExactConfiguration()
        action.execute(configuration)
        value.add(configuration)
    }

    fun contain(action: Action<in ContainConfiguration>) {
        val configuration = ContainConfiguration()
        action.execute(configuration)
        value.add(configuration)
    }
}