plugins {
    id("spring-boot-conventions")
    id("module-dependencies-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:mybatis"))
    implementation(project(":commons:websecurity"))
    implementation(project(":commons:login-core"))

    implementation(project(":domains:policy"))
    implementation(project(":domains:cache"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.security:spring-security-oauth2-jose")
}
