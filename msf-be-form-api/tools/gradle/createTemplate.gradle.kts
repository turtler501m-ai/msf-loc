import org.gradle.api.InvalidUserDataException

val hexagonalDirectoryTemplate = listOf(
    "adapter/client",
    "adapter/controller",
    "adapter/repository/jpa/smartform",
    "adapter/repository/jpa/smartform/converter",
    "adapter/repository/jpa/smartform/repository",
    "adapter/repository/mybatis/msp/mapper",
    "adapter/repository/mybatis/msp/typehandler",
    "adapter/repository/mybatis/smartform/mapper",
    "adapter/repository/mybatis/smartform/typehandler",
    "application/dto",
    "application/fieldmapper",
    "application/port/in",
    "application/port/out",
    "application/service",
    "domain/code",
    "domain/dto",
    "domain/entity",
    "domain/vo",
    "support/config",
    "support/exception",
    "support/properties",
    "support/util",
)

val legacyDirectoryTemplate = listOf(
    "controller",
    "repository/msp",
    "repository/smartform",
    "dto",
    "service"
)

fun registerPackageStructureTask(
    taskName: String,
    taskDescription: String,
    usageExample: String,
    directoryTemplate: List<String>
) {
    tasks.register(taskName) {
        group = "project setup"
        description = """
            $taskDescription
            Usage: $usageExample
            """

        doLast {
            val baseDirProperty = findProperty("baseDir")?.toString()
                ?: throw InvalidUserDataException("Missing -PbaseDir. Example: ${usageExample.substringAfter("$taskName ")}")

            val baseDir = file(baseDirProperty)
            if (baseDir.exists() && !baseDir.isDirectory) {
                throw InvalidUserDataException("baseDir must be a directory path: $baseDirProperty")
            }

            val createdDirectories = linkedSetOf(baseDir.normalize().path)
            baseDir.mkdirs()

            directoryTemplate.forEach { relativePath ->
                val target = baseDir.resolve(relativePath)
                target.mkdirs()
                createdDirectories.add(target.normalize().path)
            }

            println("Created directories:")
            createdDirectories.forEach { println(" - $it") }
        }
    }
}

fun registerGradleModuleTask(
    taskName: String,
    usageExample: String
) {
    tasks.register(taskName) {
        group = "project setup"
        description = """
            Create a Gradle module skeleton under the given base directory using the given module name.
            Usage: $usageExample
            """

        doLast {
            val baseDirProperty = findProperty("baseDir")?.toString()
                ?: throw InvalidUserDataException("Missing -PbaseDir. Example: ${usageExample.substringAfter("$taskName ")}")
            val moduleName = findProperty("moduleName")?.toString()
                ?: throw InvalidUserDataException("Missing -PmoduleName. Example: ${usageExample.substringAfter("$taskName ")}")

            val baseDir = file(baseDirProperty)
            if (baseDir.exists() && !baseDir.isDirectory) {
                throw InvalidUserDataException("baseDir must be a directory path: $baseDirProperty")
            }

            if (moduleName.isBlank()) {
                throw InvalidUserDataException("moduleName must not be blank")
            }

            val packageRoot = baseDir.normalize().name
            if (packageRoot.isBlank()) {
                throw InvalidUserDataException("Unable to determine package path segment from baseDir: $baseDirProperty")
            }

            val moduleDir = baseDir.resolve(moduleName)
            val mainJavaDir = moduleDir.resolve("src/main/java/com/ktmmobile/msf/$packageRoot/$moduleName")
            val testJavaDir = moduleDir.resolve("src/test/java/com/ktmmobile/msf/$packageRoot/$moduleName")
            val mainResourcesDir = moduleDir.resolve("src/main/resources")
            val buildFile = moduleDir.resolve("build.gradle.kts")
            val gitKeepFile = mainJavaDir.resolve(".gitKeep")
            val applicationYamlFile = mainResourcesDir.resolve("application-$moduleName.yaml")
            val buildFileContent = """
                plugins {
                    id("spring-library-conventions")
                }

                dependencies {
                }
            """.trimIndent() + System.lineSeparator()
            val applicationYamlContent = """
                #### Common Profile



                ---
                #### PRD Profile
                spring:
                  config.activate.on-profile: prd



                ---
                #### STG Profile
                spring:
                  config.activate.on-profile: stg



                ---
                #### DEV Profile
                spring:
                  config.activate.on-profile: dev



                ---
                #### LOCAL Profile
                spring:
                  config.activate.on-profile: local
            """.trimIndent() + System.lineSeparator()

            val createdPaths = linkedSetOf<String>()

            listOf(baseDir, moduleDir, mainJavaDir, testJavaDir, mainResourcesDir).forEach { dir ->
                dir.mkdirs()
                createdPaths.add(dir.normalize().path)
            }

            if (!buildFile.exists()) {
                buildFile.writeText(buildFileContent)
                createdPaths.add(buildFile.normalize().path)
            } else if (buildFile.isDirectory) {
                throw InvalidUserDataException("build.gradle.kts path is a directory: ${buildFile.normalize().path}")
            }

            if (!gitKeepFile.exists()) {
                gitKeepFile.writeText("")
                createdPaths.add(gitKeepFile.normalize().path)
            } else if (gitKeepFile.isDirectory) {
                throw InvalidUserDataException(".gitKeep path is a directory: ${gitKeepFile.normalize().path}")
            }

            if (!applicationYamlFile.exists()) {
                applicationYamlFile.writeText(applicationYamlContent)
                createdPaths.add(applicationYamlFile.normalize().path)
            } else if (applicationYamlFile.isDirectory) {
                throw InvalidUserDataException("application yaml path is a directory: ${applicationYamlFile.normalize().path}")
            }

            println("Created module resources:")
            createdPaths.forEach { println(" - $it") }
            val keptPaths = buildList {
                if (buildFile.exists() && buildFile.normalize().path !in createdPaths) {
                    add(buildFile.normalize().path)
                }
                if (gitKeepFile.exists() && gitKeepFile.normalize().path !in createdPaths) {
                    add(gitKeepFile.normalize().path)
                }
                if (applicationYamlFile.exists() && applicationYamlFile.normalize().path !in createdPaths) {
                    add(applicationYamlFile.normalize().path)
                }
            }

            if (keptPaths.isNotEmpty()) {
                println("Existing files kept:")
                keptPaths.forEach { println(" - $it") }
            }
        }
    }
}

registerGradleModuleTask(
    taskName = "createGradleModule",
    usageExample = "./gradlew createGradleModule -PbaseDir=domains -PmoduleName=_sample"
)

registerPackageStructureTask(
    taskName = "createHexagonalPackages",
    taskDescription = "Create fixed hexagonal package directories under the given base directory.",
    usageExample = "./gradlew createHexagonalPackages -PbaseDir=domains/form/src/main/java/com/ktmmobile/msf/domains/_sample",
    directoryTemplate = hexagonalDirectoryTemplate
)

registerPackageStructureTask(
    taskName = "createLegacyPackages",
    taskDescription = "Create fixed legacy package directories under the given base directory.",
    usageExample = "./gradlew createLegacyPackages -PbaseDir=domains/form/src/main/java/com/ktmmobile/msf/domains/_sample",
    directoryTemplate = legacyDirectoryTemplate
)
