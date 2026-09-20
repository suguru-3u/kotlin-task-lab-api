# 概要

このプロジェクトは学習用にTODOアプリのバックエンドを開発することを目的として作成しています。   
このプロジェクトはマルチモジュール構成で、以下のモジュールで構成されています。

- task-lab-api: TODOアプリのバックエンドAPIを提供するモジュール
- task-lab-core: TODOアプリのドメインモデルやユーティリティを提供するモジュール
- task-lab-infrastructure: TODOアプリのインフラ層（DBスキーマ定義やFlywayマイグレーション）を提供するモジュール

今後作る機能とロードマップ: `doc/ROADMAP.md`

# コアな技術スタック

以下の技術を使用しています。
選定理由は実務で使用している技術であり、学習に適しているため使用しています。
※ testcontainersはチャレンジ枠で採用しました。

- Spring Boot 4.1.0
- testcontainers（Spring Boot BOMに内容）
- MySQL（Spring Boot BOMに内容）
- Kotlin 2.3.21
- kotest 6.1.11
- ktlint 14.2.0

# アーキテクチャ

複数モジュール構成を採用し、`task-lab-core`モジュールにドメインを集約、依存関係の向き先を`task-lab-core`
に向かうようにしています。     
このようにDDDとクリーンアーキテクチャ・ヘキサゴナルアーキテクチャの考え方を取り入れています。

また今後マイクロサービス化することも意識して、パッケージ構成をレイヤーではなくドメイン毎にパッケージしています。

# 動作に必要なもの

動作には、Gradle・Docker・MySQLを使用できる環境が必要です。

# 起動手順

アプリケーションを起動するには、`task-lab-api`モジュールを起動させてAPI通信を行えるようにする必要があります。

```
./gradlew :task-lab-api:bootRun
```

テストを実行するには、各モジュールで以下のコマンドを実行してください。

```
./gradlew test
./gradlew :task-lab-api:test
./gradlew :task-lab-api:integrationTest
./gradlew :task-lab-core:test
```

Lintを実行するには、各モジュールで以下のコマンドを実行してください。

```
./gradlew ktlintCheck
./gradlew :モジュール名:ktlintCheck
```

# DB マイグレーション

DBのマイグレーションにはFlywayを使用しています。
`task-lab-infrastructure/src/main/resources/db/migration`に移動して、`V{バージョン番号}__{説明}.sql`
の命名規則でSQLファイルを作成してください。  
その後、`task-lab-api`モジュールを起動すると自動でマイグレーションが実行されます。   
また手動で`flyway migrate`を実行することでもマイグレーションを実行できます。

# CIについて

CIはGitHub Actionsを使用しています。
プッシュやプルリクエスト時に自動でテストが実行されます。
対象は各モジュールのユニットテスト・統合テスト・Lintチェックです。
