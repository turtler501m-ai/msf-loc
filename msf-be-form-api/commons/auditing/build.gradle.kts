plugins {
    id("spring-library-conventions")
}

val aopVersion = findProperty("aop.spring.boot.starter.version") as String

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-aop:${aopVersion}")
}
