plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:client"))
    implementation(project(":commons:mybatis"))
    implementation(project(":commons:websecurity"))
    implementation(project(":commons:crypto"))
    implementation(project(":commons:file"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api")
    implementation("tools.jackson.dataformat:jackson-dataformat-xml")

    runtimeOnly("com.sun.xml.bind:jaxb-impl")
}
