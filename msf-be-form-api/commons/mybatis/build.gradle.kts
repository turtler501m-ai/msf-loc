plugins {
    id("spring-library-conventions")
}

val myBatisVersion = findProperty("mybatis.spring-boot-starter.version") as String
val aopVersion = findProperty("aop.spring.boot.starter.version") as String

dependencies {
    implementation(project(":commons:common"))
    implementation(project(":commons:auditing"))

    api("org.mybatis.spring.boot:mybatis-spring-boot-starter:$myBatisVersion")

    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:$myBatisVersion")
}
