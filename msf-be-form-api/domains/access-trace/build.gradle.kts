plugins {
    id("spring-library-conventions")
    id("module-dependencies-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:mybatis"))
    implementation(project(":commons:websecurity"))
}
