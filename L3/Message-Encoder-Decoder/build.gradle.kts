plugins {
    id("java")
}

group = "ua.edu.ukma.Zhytnetsky"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Plugins
subprojects {
    tasks.register("showProjectInfo") {
        group = "help"
        description = "Prints general information about the project"

        doLast {
            println("-----------------------------------------")
            println(" Project Path: ${project.path}")
            println("         Name: ${project.name}")
            println("        Group: ${project.group}")
            println("      Version: ${project.version}")
            println("  Java version: ${JavaVersion.current()}")
            println("-----------------------------------------")
        }
    }
}

tasks.register("showAllProjectsInfo") {
    group = "help"
    description = "Prints info for every subproject"
    dependsOn(subprojects.map {
        it.tasks.named("showProjectInfo")
    })
}
