import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import proguard.gradle.ProGuardTask

version = "1.0-SNAPSHOT"

buildscript {
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.0.0")
    }
}

plugins {
    idea
    application
    val kotlinVersion = "1.4.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

application {
    mainClass.set("com.xhstormr.app.MainKt")
}

repositories {
    maven("https://mirrors.huaweicloud.com/repository/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.github.ajalt.clikt:clikt:+")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:+")

    implementation("org.json:json:+")

    implementation("com.hankcs:hanlp:+")

    implementation("com.hankcs:aho-corasick-double-array-trie:+")

    testImplementation("org.junit.jupiter:junit-jupiter:+")
}

tasks {
    val exe by creating(JavaExec::class) {
        buildDir.resolve("bin").mkdirs()
        val launch4jJar = "${ext["launch4j_home"]}/launch4j.jar"
        val launch4jCfg = "$rootDir/assets/config.xml"
        classpath = files(launch4jJar)
        args = listOf(launch4jCfg)
    }

    val proguard by creating(ProGuardTask::class) {
        val file = jar.get().archiveFile.get().asFile
        injars(file)
        outjars(file.resolveSibling("${file.nameWithoutExtension}-min.jar"))

        configuration("proguard/proguard-rules.pro")

        libraryjars("${System.getProperty("java.home")}/jmods/")
    }

    withType<Jar> {
        manifest.attributes["Main-Class"] = application.mainClass
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(configurations.runtimeClasspath.get().map { zipTree(it) })
        exclude("**/*.kotlin_module")
        exclude("**/*.kotlin_metadata")
        exclude("**/*.kotlin_builtins")
    }

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

    proguard.dependsOn(jar)
    exe.dependsOn(proguard)
}
