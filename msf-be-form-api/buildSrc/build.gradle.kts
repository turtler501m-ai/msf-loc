import java.util.*

plugins {
    `kotlin-dsl`
}

fun rootVersion(key: String): String {
    val props = Properties().apply {
        rootProject.file("../gradle.properties").inputStream().use(::load)
    }
    return props.getProperty(key)
        ?: error("Missing '$key' in root gradle.properties")
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val springBootVersion: String = rootVersion("spring.boot.version")
val dependencyManagementVersion: String = rootVersion("spring.dependency-management.version")

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    implementation("io.spring.gradle:dependency-management-plugin:$dependencyManagementVersion")
}
