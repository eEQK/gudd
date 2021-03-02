package gudd.internal

import gudd.GuddDependency
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.net.URL
import java.util.concurrent.CompletableFuture

private const val githubBaseUrl = "https://github.com"
private const val ASSETS_HEADER = "Assets"

private data class Resource(
    val href: String,
    val name: String,
)

internal class GithubDependencyDownloader {
    fun download(dependency: GuddDependency, target: File): CompletableFuture<Unit> = CompletableFuture.runAsync {
        val projectTarget = computeTargetDirectory(target, dependency)
            .also(File::mkdirs)

        val document = Jsoup.connect(parseDependencyToUrl(dependency)).get()
        val resources = extractDependencyUrlsFrom(document)

        downloadResourcesInto(projectTarget, resources)
    }.thenApply { }

    private fun computeTargetDirectory(target: File, dependency: GuddDependency) = target
        .resolve(
            dependency.groupId.split('.')
                .plus(dependency.artifactId)
                .plus(dependency.version)
                .joinToString("/")
        )

    private fun parseDependencyToUrl(dep: GuddDependency) =
        "$githubBaseUrl/${dep.author}/${dep.projectName}/packages/${dep.projectId}?version=${dep.version}"

    private fun extractDependencyUrlsFrom(document: Document) = document
        .getElementsByTag("h4")
        .first { it.hasText() && it.ownText() == ASSETS_HEADER }
        .nextElementSibling()
        .getElementsByTag("a")
        .map { Resource(it.attr("href"), it.text()) }

    private fun downloadResourcesInto(projectTarget: File, resources: List<Resource>) {
        resources.forEach {
            val file = projectTarget.resolve(it.name)

            if (file.exists()) {
                return@forEach
            } else {
                file.createNewFile()
            }

            URL(it.href).openStream().transferTo(file.outputStream())
        }
    }
}