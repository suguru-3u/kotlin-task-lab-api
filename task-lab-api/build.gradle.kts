plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "task-lab.backend"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.result)
    implementation(project(":task-lab-core"))
    runtimeOnly(project(":task-lab-infrastructure"))
    developmentOnly(libs.spring.boot.devtools)
    // 起動時に compose.yaml の MySQL を自動で up し、接続情報を自動注入する
    developmentOnly(libs.spring.boot.docker.compose)
    testImplementation(libs.spring.boot.starter.webmvc.test)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// spring-boot-docker-compose は「カレントディレクトリの compose.yaml」を探す。
// bootRun の既定は task-lab-api/ なので、compose.yaml を置いたリポジトリルートに向ける。
tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    workingDir = rootProject.projectDir
}
