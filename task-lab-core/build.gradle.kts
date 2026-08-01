plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // 依存ゼロ。Spring も JPA も入れない
}
