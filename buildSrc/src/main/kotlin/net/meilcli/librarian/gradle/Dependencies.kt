package net.meilcli.librarian.gradle

object Dependencies {

    object Kotlin {

        private const val kotlinVersion = "1.3.70"
        private const val group = "org.jetbrains.kotlin"

        val gradle = Dependency(group, "kotlin-gradle-plugin", kotlinVersion)
        val stdlib = Dependency(group, "kotlin-stdlib-jdk7", kotlinVersion)

        object Serialization {

            val gradle = Dependency(group, "kotlin-serialization", kotlinVersion)
            val runtime =
                Dependency("org.jetbrains.kotlinx", "kotlinx-serialization-runtime", "0.20.0")
        }
    }

    object Android {

        val gradle = Dependency("com.android.tools.build", "gradle", "3.6.2")

        val appCompat = Dependency("androidx.appcompat", "appcompat", "1.1.0")
        val activity = Dependency("androidx.activity", "activity", "1.1.0")
        val fragment = Dependency("androidx.fragment", "fragment", "1.2.3")
        val recyclerView = Dependency("androidx.recyclerview", "recyclerview", "1.1.0")

        val junit = Dependency("androidx.test.ext", "junit", "1.1.1")
        val espresso = Dependency("androidx.test.espresso", "espresso-core", "3.2.0")
    }

    object Librarian {

        private const val group = "net.meilcli.librarian"
        private const val version = "0.3.4"

        val pluginCore = Dependency(group, "plugin-core", version)
        val pluginPreset = Dependency(group, "plugin-preset", version)
        val uiActivity = Dependency(group, "ui-activity", version)
        val uiSerializerKotlin = Dependency(group, "ui-serializer-kotlin", version)
    }

    object Bintray {

        val plugin = Dependency("com.jfrog.bintray.gradle", "gradle-bintray-plugin", "1.8.4")
    }

    object Junit4 {

        val junit = Dependency("junit", "junit", "4.12")
    }

    object Junit5 {

        private const val version = "5.6.1"
        private const val group = "org.junit.jupiter"

        val api = Dependency(group, "junit-jupiter-api", version)
        val engine = Dependency(group, "junit-jupiter-engine", version)
    }

    object TikXml {

        private const val version = "0.8.13"
        private const val group = "com.tickaroo.tikxml"

        val core = Dependency(group, "core", version)
        val coreAnnotation = Dependency(group, "annotation", version)
        val processor = Dependency(group, "processor", version)
    }

    object SquareUp {

        val okio = Dependency("com.squareup.okio", "okio", "2.5.0")
    }
}