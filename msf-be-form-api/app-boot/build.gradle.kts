import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("spring-boot-conventions")
    id("module-dependencies-conventions")
}

dependencies {
}

tasks.named<BootJar>("bootJar") {
    archiveFileName.set("app.jar")
}
