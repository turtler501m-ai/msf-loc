rootProject.name = "msf-be-form-api"

fun includeSubModules(parent: String) {
    file(parent).listFiles()
        ?.asSequence()
        ?.filter { it.isDirectory }
        ?.filterNot { it.name.startsWith(".") }
        ?.forEach {
            include(":$parent:${it.name}")
        }
}

include("app-boot")
listOf("commons", "domains", "external").forEach(::includeSubModules)
