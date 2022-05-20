plugins {
    id("java")
}

group = "org.threetwotwo.fs"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    dependencies {

        implementation("org.apache.commons:commons-lang3:3.12.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

        configurations.all {
            exclude("junit", "junit")
        }
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}
