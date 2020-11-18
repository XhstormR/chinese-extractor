import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.xhstormr.web"
version = "1.0-SNAPSHOT"

plugins {
    idea
    application
    val kotlinVersion = "1.4.10"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.4.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

apply {
    plugin("io.spring.dependency-management")
}

application {
    applicationName = project.name
    mainClass.set("com.xhstormr.web.ApplicationKt")
}

kapt {
    useBuildCache = true
}

repositories {
    maven("https://mirrors.huaweicloud.com/repository/maven")
}

dependencies {
    runtimeOnly(kotlin("reflect"))
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    /**/

    implementation("org.springdoc:springdoc-openapi-ui:+")
    implementation("org.springdoc:springdoc-openapi-kotlin:+")
    implementation("org.springdoc:springdoc-openapi-hateoas:+")

    implementation("org.springframework.data:spring-data-commons")
    // TODO: https://github.com/spring-projects/spring-hateoas/issues/1396
    implementation("org.springframework.hateoas:spring-hateoas:1.1.1.RELEASE")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks {
    withType<Wrapper> {
        gradleVersion = "6.7"
        distributionType = Wrapper.DistributionType.ALL
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "14"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
        }
    }

    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.isFork = true
        options.isIncremental = true
        sourceCompatibility = JavaVersion.VERSION_14.toString()
        targetCompatibility = JavaVersion.VERSION_14.toString()
    }
}

/*
https://docs.gradle.org/current/userguide/kotlin_dsl.html
https://docs.gradle.org/current/userguide/java_plugin.html
*/
