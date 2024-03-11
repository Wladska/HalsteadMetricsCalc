plugins {
    id("java")
    id("org.sonarqube") version "4.4.1.3373"
    id("checkstyle")
}

group = "org.wladska"
version = "1.0-SNAPSHOT"

sonar {
  properties {
    property("sonar.projectKey", "CodeGenClash")
    property("sonar.projectName", "CodeGenClash")
    property("sonar.host.url", "http://localhost:9000")
    property("sonar.token", "sqp_e6bf59e7883e1c88770dc548087caa42a0a96375")
  }
}

checkstyle {
    configFile = file("$rootDir/config/checkstyle/checkstyle.xml")
    // Additional configuration options can be added here
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
