plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)   // all-open: @Configuration / @Transactional を CGLIB でサブクラス化可能にする
    `java-library`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    api(platform(libs.spring.boot.dependencies))  // バージョン管理のみ。Boot プラグインは適用しない
    api(project(":task-lab-core"))                // ポート型がシグネチャに出るので api

    implementation(libs.spring.boot.starter.jdbc)     // JdbcTemplate / NamedParameterJdbcTemplate / HikariCP / spring-tx
    implementation(libs.spring.boot.starter.flyway)
    runtimeOnly(libs.flyway.mysql)
    runtimeOnly(libs.mysql.connector.j)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.mysql)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}
