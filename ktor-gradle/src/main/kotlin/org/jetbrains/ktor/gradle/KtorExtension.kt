package org.jetbrains.ktor.gradle

open class KtorExtension {
    var port: Int? = null
    var jvmOptions: Array<String> = emptyArray()
    var workDir: Any? = null
}