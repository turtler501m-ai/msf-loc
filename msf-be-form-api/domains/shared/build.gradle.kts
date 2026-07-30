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
    implementation(project(":commons:login-core"))
    implementation(project(":commons:masking"))
    implementation(project(":commons:crypto"))

    implementation(project(":domains:policy"))
    implementation(project(":domains:cache"))
    implementation(project(":domains:external-client"))

    implementation("com.google.zxing:core:3.5.4")
    implementation("com.google.zxing:javase:3.5.4")
}
