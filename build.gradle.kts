import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.xhstormr.web"
version = "1.0-SNAPSHOT"

plugins {
    idea
    application
    val kotlinVersion = "1.4.0"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.3.3.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
}

apply {
    plugin("io.spring.dependency-management")
}

application {
    applicationName = project.name
    mainClassName = "com.xhstormr.web.ApplicationKt"
}

kapt {
    useBuildCache = true
}

repositories {
    maven("https://mirrors.huaweicloud.com/repository/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    runtimeOnly(kotlin("reflect"))
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    /**/

    implementation("io.springfox:springfox-boot-starter:+")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
}

tasks {
    withType<Wrapper> {
        gradleVersion = "6.6"
        distributionType = Wrapper.DistributionType.ALL
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "12"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
        }
    }

    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.isFork = true
        options.isIncremental = true
        sourceCompatibility = JavaVersion.VERSION_12.toString()
        targetCompatibility = JavaVersion.VERSION_12.toString()
    }
}

/*
https://docs.gradle.org/current/userguide/kotlin_dsl.html
https://docs.gradle.org/current/userguide/java_plugin.html
*/
