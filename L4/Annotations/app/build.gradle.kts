plugins {
    id("java")
}

group = "ua.edu.ukma.Zhytnetsky"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    compileOnly("org.projectlombok:lombok:1.18.38")

    annotationProcessor(project(":annotation-processor"))
    compileOnly(project(":annotation-processor"))

    implementation("org.hibernate.validator:hibernate-validator:9.0.1.Final")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")
    implementation("org.glassfish:jakarta.el:5.0.0-M1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
