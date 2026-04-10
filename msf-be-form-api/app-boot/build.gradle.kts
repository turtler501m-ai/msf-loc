plugins {
    id("spring-boot-conventions")
    id("module-dependencies-conventions")
}

extra["commonsModuleExcludes"] = setOf("auditing")

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":external:websecurity"))
    implementation(project(":external:mybatis"))
}
