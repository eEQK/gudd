package gudd

open class GuddPluginExtension {
    val dependencies = mutableListOf<GuddDependency>()

    var downloadPath = ".gudd"
    var repositoryName = "GuddRepo"

    fun implementation(gradleNotation: String, githubNotation: String) {
        val (groupId, artifactId, version) = gradleNotation.split(":")
        val (author, projectName, projectId) = githubNotation.split(":")
        implementation(groupId, artifactId, author, projectName, projectId, version)
    }

    private fun implementation(
        groupId: String,
        artifactId: String,
        author: String,
        projectName: String,
        projectId: String,
        version: String
    ) {
        dependencies.add(GuddDependency(DependencyScope.IMPLEMENTATION, groupId, artifactId, version, author, projectName, projectId))
    }

    fun api(gradleNotation: String, githubNotation: String) {
        val (groupId, artifactId, version) = gradleNotation.split(":")
        val (author, projectName, projectId) = githubNotation.split(":")
        api(groupId, artifactId, author, projectName, projectId, version)
    }

    private fun api(
        groupId: String,
        artifactId: String,
        author: String,
        projectName: String,
        projectId: String,
        version: String
    ) {
        dependencies.add(GuddDependency(DependencyScope.API, groupId, artifactId, version, author, projectName, projectId))
    }
}