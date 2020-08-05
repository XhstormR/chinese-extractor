rootProject.name = "text-extractor-backend"

pluginManagement {
    repositories {
        maven("https://mirrors.huaweicloud.com/repository/maven")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
    }
}

plugins {
    `gradle-enterprise`
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(extra["buildScan"].toString().toBoolean())
    }
}
