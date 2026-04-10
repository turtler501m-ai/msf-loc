plugins {
    id("spring-library-conventions")
}

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":external:websecurity"))
    implementation(project(":external:mybatis"))

    //local 구동을 위한 임시 추가
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("commons-lang:commons-lang:2.6")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
    implementation("org.jdom:jdom:1.1.3") // Replaced jdom2 with jdom 1.x
    implementation("commons-httpclient:commons-httpclient:3.1")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.apache.commons:commons-dbcp2:2.9.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    runtimeOnly("com.sun.xml.bind:jaxb-impl:3.0.2")
}
