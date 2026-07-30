plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))

    api("org.springframework.boot:spring-boot-starter-batch")
}
