package net.meilcli.librarian.gradle

object Dependencies {

    object Kotlin {

        private const val kotlinVersion = "1.3.70"
        private const val group = "org.jetbrains.kotlin"

        val gradle = Dependency(group, "kotlin-gradle-plugin", kotlinVersion)
        val stdlib = Dependency(group, "kotlin-stdlib-jdk7", kotlinVersion)
        val reflect = Dependency(group, "kotlin-reflect", kotlinVersion)

        object Serialization {

            val gradle = Dependency(group, "kotlin-serialization", kotlinVersion)
            val runtime =
                Dependency("org.jetbrains.kotlinx", "kotlinx-serialization-runtime", "0.20.0")
        }
    }

    object Android {

        val gradle = Dependency("com.android.tools.build", "gradle", "3.5.3")
        val junit = Dependency("androidx.test.ext", "junit", "1.1.1")
        val espresso = Dependency("androidx.test.espresso", "espresso-core", "3.2.0")
    }

    object Librarian {

        private const val group = "net.meilcli.librarian"
        private const val version = "0.2.1"

        val pluginCore = Dependency(group, "plugin-core", version)
        val plguinPreset = Dependency(group, "plugin-preset", version)
    }

    object Bintray {

        val plugin = Dependency("com.jfrog.bintray.gradle", "gradle-bintray-plugin", "1.8.4")
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

        val annotation = Dependency(group, "annotation", version)
        val core = Dependency(group, "core", version)
        val processor = Dependency(group, "processor", version)
    }

    object SquareUp {

        val okio = Dependency("com.squareup.okio", "okio", "2.5.0")
    }
}