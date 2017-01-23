package org.jetbrains.ktor.gradle

import org.gradle.api.*

class KtorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ktor = project.extensions.create("ktor", KtorExtension::class.java)

        project.afterEvaluate {
            if (ktor.port != null) {
                val ktorRun = project.tasks.create("ktor-start", KtorStartStopTask::class.java) { t ->
                    t.description = "Run ktor server"
                    t.group = KtorGroup
                }

                val ktorStop = project.tasks.create("ktor-stop", KtorStartStopTask::class.java) { t ->
                    t.start = false
                    t.description = "Stop ktor server"
                    t.group = KtorGroup
                }

                ktorRun.dependsOn(project.tasks.getByName("assemble"))
            }
        }
    }

    companion object {
        val KtorGroup = "KTor"
    }
}