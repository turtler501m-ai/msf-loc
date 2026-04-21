plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))

    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.springframework.boot:spring-boot-starter-json")
}
