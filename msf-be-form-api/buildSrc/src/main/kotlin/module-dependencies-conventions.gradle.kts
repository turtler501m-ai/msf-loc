if (project.path == ":app-boot") {
    afterEvaluate {
        val excludedCommons = (extensions.extraProperties.properties["commonsModuleExcludes"] as? Set<*>)
            ?.mapNotNull { it?.toString() }
            ?.toSet()
            ?: emptySet()
        val excludedDomains = (extensions.extraProperties.properties["domainsModuleExcludes"] as? Set<*>)
            ?.mapNotNull { it?.toString() }
            ?.toSet()
            ?: emptySet()
        val excludedExternals = (extensions.extraProperties.properties["externalModuleExcludes"] as? Set<*>)
            ?.mapNotNull { it?.toString() }
            ?.toSet()
            ?: emptySet()

        val commonProjects = rootProject.subprojects
            .filter { it.path.startsWith(":commons:") }
            .filter { it.buildFile.exists() }
            .filterNot { excludedCommons.contains(it.name) || excludedCommons.contains(it.path) }

        val domainProjects = rootProject.subprojects
            .filter { it.path.startsWith(":domains:") }
            .filter { it.buildFile.exists() }
            .filterNot { excludedDomains.contains(it.name) || excludedDomains.contains(it.path) }

        val externalProjects = rootProject.subprojects
            .filter { it.path.startsWith(":external:") }
            .filter { it.buildFile.exists() }
            .filterNot { excludedExternals.contains(it.name) || excludedExternals.contains(it.path) }

        dependencies {
            commonProjects.forEach { add("implementation", project(it.path)) }
            domainProjects.forEach { add("implementation", project(it.path)) }
            externalProjects.forEach { add("implementation", project(it.path)) }
        }
    }
}
