
val springBootVersion by extra("2.2.1.RELEASE")
val kotlinVersion by extra ("1.3.60")
val kotlinCoroutinesVersion by extra("1.3.2")

plugins {
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "auth-server"

version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "Hoxton.RELEASE"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    if (project.name != "commons") {

        extra["springCloudVersion"] = "Hoxton.RELEASE"
        apply(plugin = "kotlin")
        apply(plugin = "kotlin-spring")
        apply(plugin = "kotlin-kapt")
        apply(plugin = "org.springframework.boot")
        apply(plugin = "io.spring.dependency-management")

        dependencyManagement {
            imports {
                mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.RELEASE")
            }
        }

        dependencies {
            implementation(project(":commons"))
            implementation("org.springframework.boot:spring-boot-starter-actuator")
            implementation("org.springframework.boot:spring-boot-starter-rsocket")
            implementation("org.projectlombok:lombok")

            annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        }
    }
}