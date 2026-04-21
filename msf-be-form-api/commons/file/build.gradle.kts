plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:websecurity"))

    implementation(platform("software.amazon.awssdk:bom:${property("aws.sdk.version")}"))
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:apache-client")
    implementation("org.apache.tika:tika-core:${property("tika.version")}")
}
