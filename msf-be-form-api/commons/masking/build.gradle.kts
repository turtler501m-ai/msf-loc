plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))

    implementation("org.springframework.boot:spring-boot-starter-json")
}
