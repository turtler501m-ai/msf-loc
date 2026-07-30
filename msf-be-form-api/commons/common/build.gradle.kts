plugins {
    id("spring-library-conventions")
}

val reflectionsVersion = findProperty("reflections.version") as String
val log4jdbcVersion = findProperty("log4jdbc.version") as String
val shedlockVersion = findProperty("shedlock.version") as String

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.boot:spring-boot-starter-aspectj")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.springframework:spring-tx")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.oracle.database.jdbc:ojdbc11")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    api("net.javacrumbs.shedlock:shedlock-spring:$shedlockVersion")
    implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:$shedlockVersion")

    implementation("org.reflections:reflections:$reflectionsVersion")

    implementation("org.springframework.boot:spring-boot-starter-opentelemetry")
    implementation("io.micrometer:micrometer-registry-prometheus")

    implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:$log4jdbcVersion")
}
