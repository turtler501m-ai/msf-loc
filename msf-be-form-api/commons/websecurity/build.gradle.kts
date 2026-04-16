plugins {
    id("spring-library-conventions")
}

val jasyptVersion = findProperty("jasypt.version") as String

dependencies {
    implementation(project(":commons:common"))

    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:${jasyptVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
}
