plugins {
    id("java")
}

group = "com.naijun.rhino"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    implementation("org.mozilla:rhino:1.7.14")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}