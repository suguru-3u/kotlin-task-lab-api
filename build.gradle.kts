//apply false の主目的は「配置」、副次的に「バージョン集約」
//
//plugins {} ブロックは、宣言した時点でそのプラグインを ビルドスクリプトのクラスパスに載せます。apply false は「クラスパスには載せるが、このプロジェクトには適用しない」という意味です。
//
//つまりルートで apply false する第一の目的は、
//
//1. ルートプロジェクト自体には Spring Boot プラグインを適用しない（→ src の無いルートで :bootJar が失敗する問題が消える）
//2. 子プロジェクトがバージョン指定なしで id("org.springframework.boot") と書けるようになる

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
}
