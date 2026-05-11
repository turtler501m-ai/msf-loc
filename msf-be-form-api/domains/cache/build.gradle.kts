plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:cache-core"))
    implementation(project(":commons:websecurity"))
    implementation(project(":commons:mybatis"))
    implementation(project(":domains:policy"))
}
