plugins {
    id("spring-library-conventions")
}

val shedlockVersion = findProperty("shedlock.version") as String

dependencies {
    api(project(":commons:common"))

    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("net.javacrumbs.shedlock:shedlock-spring:$shedlockVersion")
    implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:$shedlockVersion")
}
