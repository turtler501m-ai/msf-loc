plugins {
    java
    `java-library`
    pmd
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

val lombokVersion: String = providers.gradleProperty("lombok.version").get()
val mapstructVersion: String = providers.gradleProperty("mapstruct.version").get()

dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.processResources {
    from("src/main/java") {
        include("**/*.xml")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
}

tasks.test {
    useJUnitPlatform()
}

pmd {
    toolVersion = providers.gradleProperty("pmd.version").get()
    isConsoleOutput = true
    isIgnoreFailures = true
    ruleSets = emptyList()
    ruleSetFiles = files(rootProject.layout.projectDirectory.file("tools/pmd/ktds_PMD_RuleSet_v1.5_v7.xml"))
}

tasks.withType<Pmd>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.named("pmdTest").configure {
    enabled = false
}
