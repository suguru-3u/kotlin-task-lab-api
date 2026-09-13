# 概要

DB管理には、Flywayを使用しています。

DBスキーマの変更をSQLファイル（or Javaコード）として履歴管理し、アプリ起動時などに自動適用するマイグレーションツールです。
「DBスキーマもコードと同じようにバージョン管理する」という考え方（Migration-based schema management）を実現します。

# 仕組み

1.

バージョン管理されたSQLファイル                                                                                                                                                                                                                                 
V{バージョン}__{説明}.sql という命名規則でファイルを置くだけです（このプロジェクトなら V1__create_tasks.sql）。

- V = Versioned migration（一度だけ適用、以後不変）
- R = Repeatable migration（ファイルの中身が変わるたびに毎回再実行、ビューやストアド定義向け）
- バージョン番号は 1, 1.1, 2 のように順序さえ守れば自由

2. flyway_schema_history
   テーブル                                                                                                                                                                                                                                  
   Flywayは対象DBにこの管理テーブルを自動作成し、各マイグレーションについて「バージョン・ファイル名・チェックサム・適用日時・成功/失敗」を記録します。    
   次回起動時はこのテーブルを見て、まだ適用していないバージョンだけを実行します。

3.

チェックサム検証                                                                                                                                                                                                                                                
適用済みのファイルを後から書き換えると、記録されているチェックサムと不一致になり、次回migrate実行時にエラーで止まります。   
flyway repairで強制的にチェックサムを再計算・整合させることも可能です。

4. migrate / baseline / repair などのコマンド（内部動作）

- migrate: 未適用のマイグレーションを順番に実行
- baseline: 既存の（Flyway管理外だった）DBに「ここから管理開始」という起点を打つ
- validate: チェックサム不整合やファイル欠落を検出
- clean: 全テーブル削除（本番では基本使わない危険なコマンド）

このプロジェクトでの使われ方

task-lab-api/src/main/resources/application.properties:5-6
の設定の通り:                                                                                                                                                                                           
spring.flyway.enabled=true                                                                                                                                                                                                                                         
spring.flyway.locations=classpath:db/migration

- spring-boot-starter-flyway によりSpring Boot起動時に自動で migrate が実行される（手動でCLIを叩く必要がない）
- 実体のSQLファイルは task-lab-infrastructure モジュール側の src/main/resources/db/migration/ に置かれ、classpath経由で
  task-lab-api から読み込まれる（インフラ層にスキーマ定義を閉じ込める構成）
- DB接続情報自体は spring-boot-docker-compose が compose.yaml から自動注入するため、Flyway用のURL/認証情報も明示設定不要

補足：Community版とTeams版

無料のCommunity Edition（このプロジェクトが使っているもの）はMySQL/PostgreSQLなど主要DBの基本機能をカバーします。Undo
migration、複数スキーマの高度な制御などはTeams（有償）版の機能です。  
