package gudd

enum class DependencyScope { IMPLEMENTATION, API }

data class GuddDependency(
    val scope: DependencyScope,

    val groupId: String,
    val artifactId: String,
    val version: String,

    val author: String,
    val projectName: String,
    val projectId: String,
) {
    fun toGradleNotation(): String = "$groupId:$artifactId:$version"
    fun toGithubNotation(): String = "$author:$projectName:$projectId"
}
