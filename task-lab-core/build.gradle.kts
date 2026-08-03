plugins {
    alias(libs.plugins.kotlin.jvm)
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
    // Spring も JPA も入れない。
    // JSR-330 は JCP 標準仕様のアノテーション（6クラス・推移依存ゼロ）なので、
    // @Named を付けても core は Guice / Dagger / CDI などにそのまま載せられる状態のまま。
    //
    // api ではなく implementation でよい。consumer が @Named をコンパイル時に見る必要はなく、
    // Spring が実行時にアノテーションを読めればよい（implementation は runtimeClasspath には伝播する）。
    implementation(libs.jakarta.inject.api)
    implementation(libs.kotlin.result)
}
