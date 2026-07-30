plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))

    api("org.apache.poi:poi-ooxml:5.5.1")
    implementation("org.springframework:spring-context")
    compileOnly("jakarta.servlet:jakarta.servlet-api")
}
