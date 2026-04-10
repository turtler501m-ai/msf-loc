plugins {
    id("java-conventions")
    id("io.spring.dependency-management")
}

val springBootVersion: String = providers.gradleProperty("spring.boot.version").get()

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}
