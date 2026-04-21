plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:mybatis"))
    implementation(project(":commons:auditing"))

    implementation("org.springframework.boot:spring-boot-http-client")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.core:jackson-databind")
}
