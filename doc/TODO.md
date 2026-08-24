# TODO コメント一覧

リポジトリ内に残っている TODO コメントの一覧です。各項目に、TODO コメントの原文と該当箇所のコードを記載しています。

- 更新日: 2026-08-23
- 対象: リポジトリ全体（`.git` / `build` / `.gradle` / `.idea` を除く）
- 件数: 20 件（実装 11 件 / 学習 9 件）

> 注: `README.md` 内の「TODO アプリ」という記述はアプリ名の一部であり、TODO コメントではないため除外しています。

種別の意味:

- **実装** … コードやドキュメントに手を入れて解消するタスク
- **学習** … 仕組みや設計判断を理解するための調査メモ（コードは変わらないこともある）

## 目次

| # | 種別 | ファイル | 行 | 概要 |
| --- | --- | --- | --- | --- |
| 1 | 実装 | `task-lab-api/.../task/RegisterController.kt` | 20 | 例外処理の見直し・ControllerAdvice の作成 |
| 2 | 実装 | `task-lab-api/.../task/RegisterController.kt` | 21 | logger の導入 |
| 3 | 実装 | `task-lab-api/.../task/RegisterController.kt` | 35 | バリデーションエラーのレスポンスをカスタマイズ |
| 4 | 実装 | `task-lab-api/.../task/TaskExceptionHandler.kt` | 12 | レスポンスの型の種類を整理する |
| 5 | 実装 | `task-lab-api/.../task/UpdateTaskController.kt` | 15 | スタイルガイドの導入を検討 |
| 6 | 実装 | `task-lab-api/.../task/UpdateTaskController.kt` | 20 | 音声入力の対応 |
| 7 | 実装 | `task-lab-api/.../task/UpdateTaskController.kt` | 21 | ID の値オブジェクト化と Input クラスの作成 |
| 8 | 学習 | `task-lab-api/.../task/UpdateTaskController.kt` | 50 | クラス・関数のスコープの学習 |
| 9 | 実装 | `task-lab-core/.../domainService/TaskFoundDomainService.kt` | 12 | タスク存在確認のドメインサービス作成 |
| 10 | 学習 | `task-lab-core/.../task/RegisterTaskInteractor.kt` | 19 | Kotlin-Result の使い方 |
| 11 | 学習 | `task-lab-core/.../task/RegisterTaskInteractor.kt` | 32 | sealed と object の使い分け |
| 12 | 学習 | `task-lab-core/.../task/UpdateTaskUseCase.kt` | 7 | パッケージ構成の学習 |
| 13 | 学習 | `task-lab-infrastructure/.../TaskFoundJdbcAdapter.kt` | 12 | 例外（非チェック例外）の扱い |
| 14 | 学習 | `task-lab-infrastructure/.../TaskFoundJdbcAdapter.kt` | 13 | ラムダ式の理解 |
| 15 | 実装 | `task-lab-api/src/test/.../TaskLabApiApplicationTests.kt` | 7 | Testcontainers 導入後に `@Disabled` を外す |
| 16 | 学習 | `task-lab-api/src/integrationTest/.../IntegrationTestBase.kt` | 11 | kotest と kotlin-test-junit5 の違い |
| 17 | 実装 | `task-lab-api/src/integrationTest/.../TaskRegisterTest.kt` | 17 | テストの技術選定を md にまとめる |
| 18 | 学習 | `task-lab-api/src/integrationTest/.../TaskRegisterTest.kt` | 20 | IT に必要な設定が UT で不要な理由 |
| 19 | 学習 | `task-lab-api/src/integrationTest/.../TaskRegisterTest.kt` | 30 | `this as` が何をしているのか |
| 20 | 実装 | `README.md` | 8 | プロジェクトのマイクロサービス化 |

---

## task-lab-api

### 1. 例外処理の見直し・ControllerAdvice の作成

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/RegisterController.kt:20`
- TODO: `ここの例外処理を見直す。controllerのadviceのクラスを作成する`

> **対応状況: ほぼ完了** — `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/TaskExceptionHandler.kt` に `@RestControllerAdvice` が作成済みで、`IllegalArgumentException` / `BadRequestException` をハンドリングしている。残タスクは、Controller 側で `getOrElse` / `getOrThrow` から例外を投げ直している現在の書き方を見直すかどうかの判断と、不要になった TODO コメントの削除。

```kotlin
fun execute(@RequestBody request: Request) {
    // TODO: ここの例外処理を見直す。controllerのadviceのクラスを作成する
    // TODO: ログにloggerを使用するようにしてもいいかもしれない
    val task = RegisterTaskUseCase.Input(
        task = Task.fromCreateRequest(
            title = request.title,
            description = request.description
        ).getOrElse {
            throw BadRequestException("Invalid request")
        }
    )
    registerTaskUseCase.execute(input = task).getOrThrow {
        throw IllegalArgumentException("Invalid request")
    }
}
```

### 2. logger の導入

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/RegisterController.kt:21`
- TODO: `ログにloggerを使用するようにしてもいいかもしれない`

現状ログ出力は `TaskFoundDomainService` の `print` のみで、Controller 層にはログがない。SLF4J などのロガー導入を検討する。

### 3. バリデーションエラーのレスポンスをカスタマイズ

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/RegisterController.kt:35`
- TODO: `バリデーションエラーが発生した際にのエラーレスポンスをカスタマイズする`

```kotlin
// TODO: バリデーションエラーが発生した際にのエラーレスポンスをカスタマイズする
class Request(
    val title: String,
    val description: String,
)
```

`Request` にはまだ Bean Validation のアノテーションが付いておらず、バリデーションはドメイン層（`Task.fromCreateRequest`）で行われている。どちらで検証し、どう返すかを決める必要がある。項目 4 と関連。

### 4. レスポンスの型の種類を整理する

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/TaskExceptionHandler.kt:12`
- TODO: `レスポンスの型に種類がありそう`

```kotlin
@RestControllerAdvice
class TaskExceptionHandler {

    // TODO: レスポンスの型に種類がありそう
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ErrorResponse {
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, "サーバーでエラーが発生")
    }
```

現在は Spring の `ErrorResponse` を返している。`ProblemDetail` / `ResponseEntity` / 独自クラスなどの選択肢を比較して方針を決める。項目 3 と関連。

### 5. スタイルガイドの導入を検討

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/UpdateTaskController.kt:15`
- TODO: `スタイルガイドの導入を検討する`

ktlint / detekt などの導入検討。`RegisterController` は `val`、`UpdateTaskController` は `private val` でコンストラクタ引数を受けているなど、モジュール間で書き方が揃っていない箇所がある。

### 6. 音声入力の対応

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/UpdateTaskController.kt:20`
- TODO: `音声入力を使用できるようにしてもいいかも`

アイデアレベルの機能案。

### 7. ID の値オブジェクト化と Input クラスの作成

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/UpdateTaskController.kt:21`
- TODO: `IDの値オブジェクトを作成して、Inputクラスを作成する`

```kotlin
fun execute(@PathVariable taskId: String, @RequestBody request: Request): Response {
    // TODO: 音声入力を使用できるようにしてもいいかも
    // TODO: IDの値オブジェクトを作成して、Inputクラスを作成する
    val input = UpdateTaskUseCase.Input(
        task = Task.fromUpdateRequest(
            id = taskId,
            title = request.title,
            description = request.description
        ).getOrElse {
            throw IllegalArgumentException("Invalid request")
        }
    )
```

`tasklab.core.domain.task.TaskId` は既に存在するが、Controller では `taskId` を `String` のまま扱っている。

### 8. クラス・関数のスコープの学習

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/UpdateTaskController.kt:50`
- TODO: `クラスや関数のスコープについて学習する`

```kotlin
// TODO: クラスや関数のスコープについて学習する
class Response(
    val taskId: String,
    val title: String,
    val description: String,
)
```

Kotlin の可視性修飾子（`public` / `internal` / `protected` / `private`）とネストクラスの扱い。

---

## task-lab-core

### 9. タスク存在確認のドメインサービス作成

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/domain/task/domainService/TaskFoundDomainService.kt:12`
- TODO: `タスクが存在するのか確認するドメインサービスを作成する`（補足: `先にインターフェースを作成する必要がありそう。`）

```kotlin
// TODO: タスクが存在するのか確認するドメインサービスを作成する
// 先にインターフェースを作成する必要がありそう。
@Named
class TaskFoundDomainService(
    private val taskFoundRepositoryPort: TaskFoundRepositoryPort
) {
    // 2件のタスクが見つかったらログを残してエラー型を返す
    // そのほかの例外の場合、ログを残してエラー型を返す
    // 正常の場合、タスクをレスポンスする
    fun execute(taskId: TaskId): Result<Task, FailureTaskNotFound> { ... }
```

クラス本体は実装済み。残っているのは冒頭コメントにある「先にインターフェースを作成する」判断と、コメント内に書かれた「2 件見つかった場合」の分岐が未実装である点。

### 10. Kotlin-Result の使い方

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/usecase/task/RegisterTaskInteractor.kt:19`
- TODO: `Kotlin-Resultの使い方〜`

```kotlin
// TODO: Kotlin-Resultの使い方〜
@Named
class RegisterTaskInteractor(
    private val taskRegisterRepositoryPort: TaskRegisterRepositoryPort,
) : RegisterTaskUseCase {
    override fun execute(input: RegisterTaskUseCase.Input): Result<Unit, FailureRegisterTask> {
        return runCatching {
            taskRegisterRepositoryPort.execute(task = input.task)
        }.orElse {
            Err(FailureRegisterTask)
        }
    }
```

`com.github.michaelbull.result` の `runCatching` / `orElse` / `getOrElse` / `getOrThrow` の使い分け。

### 11. sealed と object の使い分け

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/usecase/task/RegisterTaskInteractor.kt:32`
- TODO: `こういった場合にsealdなのかobjectを使用するのか判断できるようになりたい`

```kotlin
// TODO: こういった場合にsealdなのかobjectを使用するのか判断できるようになりたい
data object FailureRegisterTask
```

なお `TaskFoundDomainService` の `FailureTaskNotFound` は `object`、こちらは `data object` と書き方が揃っていないため、あわせて整理するとよい。

### 12. パッケージ構成の学習

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/usecase/task/UpdateTaskUseCase.kt:7`
- TODO: `パッケージ構成についても学ぶ必要がありそう。`

```kotlin
// TODO: パッケージ構成についても学ぶ必要がありそう。

interface UpdateTaskUseCase {
```

現状は `domain` / `port` / `usecase` に分けたヘキサゴナル寄りの構成。`domainService` だけがキャメルケースであるなど命名も混在している。

---

## task-lab-infrastructure

### 13. 例外（非チェック例外）の扱い

- ファイル: `task-lab-infrastructure/src/main/kotlin/tasklab/infrastructure/persistence/TaskFoundJdbcAdapter.kt:12`
- TODO: `発生する例外に対する対処方法わかっているのか？（非チェック例外の対処方法など）`

### 14. ラムダ式の理解

- ファイル: `task-lab-infrastructure/src/main/kotlin/tasklab/infrastructure/persistence/TaskFoundJdbcAdapter.kt:13`
- TODO: `ラムダ式について理解している？`

```kotlin
// TODO: 発生する例外に対する対処方法わかっているのか？（非チェック例外の対処方法など）
// TODO: ラムダ式について理解している？

@Repository
class TaskFoundJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : TaskFoundRepositoryPort {

    @Transactional
    override fun execute(taskId: TaskId): Task {
        // 中略
        return jdbcTemplate.queryForObject(sql, params) { rs, _ ->
            Task.fromRepository(
                id = taskId,
                title = rs.getString("title"),
                description = rs.getString("description")
            )
        }
    }
}
```

`queryForObject` に渡している `RowMapper` の trailing lambda と、`EmptyResultDataAccessException` などの非チェック例外の扱いが対象。

---

## テスト

### 15. Testcontainers 導入後に `@Disabled` を外す

- ファイル: `task-lab-api/src/test/kotlin/task_lab/backend/task_lab_api/TaskLabApiApplicationTests.kt:7`
- TODO: `Testcontainers を導入したら @Disabled を外す。`

```kotlin
// TODO: Testcontainers を導入したら @Disabled を外す。
//  spring-boot-docker-compose は developmentOnly のためテストのクラスパスに載らず、
//  仮に載せても spring.docker.compose.skip.in-tests が既定 true なのでコンテナは起動しない。
//  そのため現状このテストは DataSource を生成できず必ず失敗する。
@Disabled("DataSource が必要。Testcontainers 導入後に有効化する")
@SpringBootTest
class TaskLabApiApplicationTests {
```

> **補足**: `integrationTest` ソースセットでは `MySqlContainerConfig` により Testcontainers が既に動いている。この `src/test` 側の `contextLoads` を有効化するのか、IT 側に任せて削除するのかを決める。

### 16. kotest と kotlin-test-junit5 の違い

- ファイル: `task-lab-api/src/integrationTest/kotlin/task_lab/backend/task_lab_api/IntegrationTestBase.kt:11`
- TODO: `kotestとkotlin-test-junit5の違いを理解する`

```kotlin
// TODO: kotestとkotlin-test-junit5の違いを理解する

@SpringBootTest
@AutoConfigureMockMvc
@Import(MySqlContainerConfig::class)
abstract class IntegrationTestBase {
```

`IntegrationTestBase` は JUnit5（`@BeforeEach`）ベース、`TaskRegisterTest` は kotest の `FreeSpec` ベースと 2 系統が混在している。項目 17 と関連。

### 17. テストの技術選定を md にまとめる

- ファイル: `task-lab-api/src/integrationTest/kotlin/task_lab/backend/task_lab_api/TaskRegisterTest.kt:17`
- TODO: `テストコードの公式サイト、技術選定に関してmdファイルにまとめる`

```kotlin
// TODO: テストコードの公式サイト、技術選定に関してmdファイルにまとめる
// https://kotest.io/docs/framework/project-setup.html
```

`doc/` 配下に UT / IT の使い分けとライブラリ選定理由をまとめるドキュメントを作成する。項目 16・18 の結論をここに集約するとよい。

### 18. IT に必要な設定が UT で不要な理由

- ファイル: `task-lab-api/src/integrationTest/kotlin/task_lab/backend/task_lab_api/TaskRegisterTest.kt:20`
- TODO: `なぜこの設定が必要なのか？　UTではなぜ不要なのか？`

```kotlin
// TODO: なぜこの設定が必要なのか？　UTではなぜ不要なのか？
@SpringBootTest
@ApplyExtension(SpringExtension::class)
@Import(MySqlContainerConfig::class)
@AutoConfigureMockMvc
class TaskRegisterTest(
```

`@SpringBootTest` によるコンテキスト起動と、kotest から Spring の DI を使うための `@ApplyExtension(SpringExtension::class)` が対象。`RegisterControllerTest`（UT）は MockMvc をスタンドアロンで組んでいるため不要、という点を確認する。

### 19. `this as` が何をしているのか

- ファイル: `task-lab-api/src/integrationTest/kotlin/task_lab/backend/task_lab_api/TaskRegisterTest.kt:30`
- TODO: `このthis asについて何をしているのか調べる`

```kotlin
) : FreeSpec({
    // TODO:このthis asについて何をしているのか調べる
    this as TaskRegisterTest

    "タスクが登録できること" {
```

`FreeSpec` のコンストラクタに渡すラムダのレシーバ型と、スマートキャストによってラムダ内から `jdbcTemplate` などのプロパティにアクセスできるようになる仕組み。

---

## プロジェクト全体

### 20. マイクロサービス化

- ファイル: `README.md:8`
- TODO: `このプロジェクトをマイクロサービス化する対応を行いたい`

現状は `task-lab-api` / `task-lab-core` / `task-lab-infrastructure` のマルチモジュール構成。

```markdown
TODO：このプロジェクトをマイクロサービス化する対応を行いたい
```
