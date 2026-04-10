plugins {
    id("spring-library-conventions")
}

val springCloudVersion = findProperty("spring.cloud.version") as String
val shedlockVersion = findProperty("shedlock.version") as String
val log4jdbcVersion = findProperty("log4jdbc.version") as String
val reflectionsVersion = findProperty("reflections.version") as String
val aopVersion = findProperty("aop.spring.boot.starter.version") as String

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-aop:${aopVersion}")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.oracle.database.jdbc:ojdbc11")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.reflections:reflections:$reflectionsVersion")

    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.micrometer:micrometer-registry-prometheus")

    api("net.javacrumbs.shedlock:shedlock-spring:$shedlockVersion")
    implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:$shedlockVersion")

    implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:$log4jdbcVersion")

    runtimeOnly("org.springframework.boot:spring-boot-docker-compose")
}
