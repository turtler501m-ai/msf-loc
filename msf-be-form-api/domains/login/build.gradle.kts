plugins {
    id("spring-boot-conventions")
    id("module-dependencies-conventions")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))
    implementation(project(":commons:mybatis"))
    implementation(project(":commons:auditing"))

    implementation(project(":domains:policy"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.security:spring-security-oauth2-jose")
}
