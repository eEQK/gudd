package gudd

import gudd.internal.GithubDependencyDownloader
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencyResolutionListener
import org.gradle.api.artifacts.ResolvableDependencies
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

private const val EXTENSION_NAME = "gudd"

class GithubDependencyDownloaderPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create(EXTENSION_NAME, GuddPluginExtension::class.java)
        val resolved = AtomicBoolean(false)

        target.gradle.addListener(
            object : DependencyResolutionListener {
                override fun beforeResolve(dependencies: ResolvableDependencies) {
                    if (resolved.getAndSet(true)) return
                    resolveGuddDependencies(target, extension)
                }

                override fun afterResolve(dependencies: ResolvableDependencies) {}
            }
        )
    }

    private fun resolveGuddDependencies(target: Project, extension: GuddPluginExtension) {
        val downloadDir = target.projectDir.resolve(extension.downloadPath)
        val downloader = GithubDependencyDownloader()

        addGuddLocalRepository(target, extension, downloadDir)
        extension.dependencies.distinct().forEach { dependency ->
            blockingDownloadDependency(downloader, dependency, downloadDir)
            addGuddLocalDependency(target, dependency)
        }

    }

    private fun addGuddLocalRepository(target: Project, extension: GuddPluginExtension, downloadDir: File) {
        target.repositories.maven {
            it.name = extension.repositoryName
            it.url = target.uri(downloadDir.absolutePath)
        }
    }

    private fun blockingDownloadDependency(
        downloader: GithubDependencyDownloader,
        dependency: GuddDependency,
        downloadDir: File
    ) = downloader
        .download(dependency, target = downloadDir)
        .get()

    private fun addGuddLocalDependency(target: Project, dependency: GuddDependency) {
        target.dependencies.apply {
            this.add(dependency.scope.name.toLowerCase(), dependency.toGradleNotation())
        }
    }
}
