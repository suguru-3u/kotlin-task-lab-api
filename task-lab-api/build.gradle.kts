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


// 結合テスト専用のソースセット。src/integrationTest/kotlin が対象になる                                                                                                                                                                                        ↑
// （Kotlin プラグインが Java ソースセットごとに kotlin ディレクトリを自動で追加する）
sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

// implementation / runtimeOnly は引き継ぐが developmentOnly は引き継がない。
// spring-boot-docker-compose をテストのクラスパスに載せないため。
val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}
val integrationTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.runtimeOnly.get())
}

val integrationTest = tasks.register<Test>("integrationTest") {
    group = "verification"
    description = "Testcontainers の MySQL を使った結合テストを実行する"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter(tasks.named("test"))
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

    integrationTestImplementation(libs.spring.boot.starter.webmvc.test)  // MockMvc / JUnit5 / AssertJ
    integrationTestImplementation(libs.spring.boot.testcontainers)       // @ServiceConnection
    integrationTestImplementation(libs.testcontainers.mysql)             // MySQLContainer
    integrationTestImplementation(libs.spring.boot.starter.jdbc)         // 検証用 JdbcTemplate（runtime にしか無いのでコンパイル用に明示）
    integrationTestImplementation(libs.kotlin.test.junit5)
    integrationTestRuntimeOnly(libs.junit.platform.launcher)
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
