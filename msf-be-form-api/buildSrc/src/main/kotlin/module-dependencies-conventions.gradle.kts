if (project.path == ":app-boot") {
    afterEvaluate {
        val commonProjects = rootProject.subprojects
            .filter { it.path.startsWith(":commons:") }
            .filter { it.buildFile.exists() }

        val domainProjects = rootProject.subprojects
            .filter { it.path.startsWith(":domains:") }
            .filter { it.buildFile.exists() }

        dependencies {
            commonProjects.forEach { add("implementation", project(it.path)) }
            domainProjects.forEach { add("implementation", project(it.path)) }
        }
    }
}
