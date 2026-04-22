plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))
    implementation(project(":commons:mybatis"))
    implementation(project(":commons:auditing"))
    implementation(project(":commons:file"))
    implementation(project(":commons:client"))

    implementation(project(":domains:policy"))
}
