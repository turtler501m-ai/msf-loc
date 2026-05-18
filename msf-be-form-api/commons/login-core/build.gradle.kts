plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))

    implementation("org.springframework.boot:spring-boot-starter-aspectj")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    compileOnly("jakarta.servlet:jakarta.servlet-api")
}
