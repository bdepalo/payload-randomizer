plugins {
    id("java")
}

group = "org.threetwotwo.fs"
version = "0.1.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    dependencies {

        implementation("org.apache.commons:commons-lang3:3.17.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.12.0")

        configurations.all {
            exclude("junit", "junit")
        }
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}
