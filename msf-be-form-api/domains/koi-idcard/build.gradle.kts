plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))
    implementation(project(":commons:mybatis"))
    implementation(project(":commons:auditing"))

    implementation(project(":domains:policy"))
    implementation(project(":domains:external-client"))
}
