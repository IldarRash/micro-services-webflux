
dependencies {
    implementation(project(":commons"))
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    //spring data mongodb reactive
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.mongodb:mongodb-driver-reactivestreams")
}
