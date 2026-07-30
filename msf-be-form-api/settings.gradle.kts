rootProject.name = "msf-be-form-api"

fun moduleExcludes(parent: String): Set<String> =
    providers.gradleProperty("$parent.module.excludes")
        .orNull
        ?.split(",")
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        ?.toSet()
        ?: emptySet()

fun includeSubModules(parent: String) {
    val excludes = moduleExcludes(parent)

    file(parent).listFiles()
        ?.asSequence()
        ?.filter { it.isDirectory }
        ?.filterNot { it.name.startsWith(".") }
        ?.filterNot { it.name in excludes || ":$parent:${it.name}" in excludes }
        ?.forEach {
            include(":$parent:${it.name}")
        }
}

include("app-boot")
listOf("commons", "domains").forEach(::includeSubModules)
