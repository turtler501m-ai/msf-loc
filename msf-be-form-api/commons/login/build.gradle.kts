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
    implementation(project(":external:websecurity"))
    
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.security:spring-security-oauth2-jose")
}
