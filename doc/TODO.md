# TODO コメント一覧

リポジトリ内に残っている TODO コメントの一覧です。各項目に、TODO コメントの原文と該当箇所のコードを記載しています。

- 作成日: 2026-08-11
- 対象: リポジトリ全体（`.git` / `build` / `.gradle` / `.idea` を除く）
- 件数: 14 件

> 注: `README.md` 内の「TODO アプリ」という記述はアプリ名の一部であり、TODO コメントではないため除外しています。

## 目次

| # | ファイル | 行 | 概要 |
| --- | --- | --- | --- |
| 1 | `task-lab-api/.../task/RegisterController.kt` | 19 | 例外処理の見直し・ControllerAdvice の作成 |
| 2 | `task-lab-api/.../task/RegisterController.kt` | 20 | logger の導入 |
| 3 | `task-lab-api/.../task/RegisterController.kt` | 34 | バリデーションエラーのレスポンスをカスタマイズ |
| 4 | `task-lab-api/.../task/UpdateTaskController.kt` | 19 | 音声入力の対応 |
| 5 | `task-lab-api/.../task/UpdateTaskController.kt` | 20 | ID の値オブジェクト化と Input クラスの作成 |
| 6 | `task-lab-api/.../task/UpdateTaskController.kt` | 49 | クラス・関数のスコープの学習 |
| 7 | `task-lab-api/.../TaskLabApiApplicationTests.kt` | 7 | Testcontainers 導入後に `@Disabled` を外す |
| 8 | `task-lab-core/.../domainService/TaskFoundDomainService.kt` | 12 | タスク存在確認のドメインサービス作成 |
| 9 | `task-lab-core/.../task/RegisterTaskInteractor.kt` | 19 | Kotlin-Result の使い方 |
| 10 | `task-lab-core/.../task/RegisterTaskInteractor.kt` | 32 | sealed と object の使い分け |
| 11 | `task-lab-core/.../task/UpdateTaskUseCase.kt` | 7 | パッケージ構成の学習 |
| 12 | `task-lab-infrastructure/.../TaskFoundJdbcAdapter.kt` | 11 | 例外（非チェック例外）の扱い |
| 13 | `task-lab-infrastructure/.../TaskFoundJdbcAdapter.kt` | 12 | ラムダ式の理解 |
| 14 | `README.md` | 8 | プロジェクトのマイクロサービス化 |

---

## task-lab-api

### 1. 例外処理の見直し・ControllerAdvice の作成

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/RegisterController.kt:19`
- TODO: `ここの例外処理を見直す。controllerのadviceのクラスを作成する`

`getOrElse` / `getOrThrow` の中で `IllegalArgumentException` を直接投げており、失敗の種類にかかわらず同じ例外・同じメッセージになっている。`@RestControllerAdvice` で例外を集約してレスポンスへ変換する必要がある。

```kotlin
fun execute(@RequestBody request: Request) {
    // TODO: ここの例外処理を見直す。controllerのadviceのクラスを作成する
    // TODO: ログにloggerを使用するようにしてもいいかもしれない
    val task = RegisterTaskUseCase.Input(
        task = Task.fromCreateRequest(
            title = request.title,
            description = request.description
        ).getOrElse {
            throw IllegalArgumentException("Invalid request")
        }
    )
    registerTaskUseCase.execute(input = task).getOrThrow {
        throw IllegalArgumentException("Invalid request")
    }
}
```

### 2. logger の導入

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/RegisterController.kt:20`
- TODO: `ログにloggerを使用するようにしてもいいかもしれない`

上記 1 と同じ箇所。プロジェクト内では `TaskFoundDomainService` が `print` でログ出力しており、ロギング方針が定まっていない。

### 3. バリデーションエラーのレスポンスをカスタマイズ

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/RegisterController.kt:34`
- TODO: `バリデーションエラーが発生した際にのエラーレスポンスをカスタマイズする`

リクエストボディの `Request` クラスにバリデーションアノテーションが付いておらず、エラー時のレスポンス形式も未定義。

```kotlin
// TODO: バリデーションエラーが発生した際にのエラーレスポンスをカスタマイズする
class Request(
    val title: String,
    val description: String,
)
```

### 4. 音声入力の対応

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/UpdateTaskController.kt:19`
- TODO: `音声入力を使用できるようにしてもいいかも`

機能追加のアイデアメモ。現状は JSON ボディでの更新のみ。

### 5. ID の値オブジェクト化と Input クラスの作成

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/UpdateTaskController.kt:20`
- TODO: `IDの値オブジェクトを作成して、Inputクラスを作成する`

`taskId` を `String` のままユースケースへ渡している。`TaskId` 値オブジェクトは `task-lab-core` に存在するため、コントローラー側でも活用する余地がある。

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
    // 以下略
}
```

### 6. クラス・関数のスコープの学習

- ファイル: `task-lab-api/src/main/kotlin/task_lab/backend/task_lab_api/task/UpdateTaskController.kt:49`
- TODO: `クラスや関数のスコープについて学習する`

`Response` をネストクラスかつ public のまま定義していることに対する学習メモ。

```kotlin
// TODO: クラスや関数のスコープについて学習する
class Response @OptIn(ExperimentalUuidApi::class) constructor(
    val taskId: String,
    val title: String,
    val description: String,
)
```

### 7. Testcontainers 導入後に `@Disabled` を外す

- ファイル: `task-lab-api/src/test/kotlin/task_lab/backend/task_lab_api/TaskLabApiApplicationTests.kt:7`
- TODO: `Testcontainers を導入したら @Disabled を外す。`

コメント内に理由も併記されている。`spring-boot-docker-compose` は `developmentOnly` でテストのクラスパスに載らず、載せても `spring.docker.compose.skip.in-tests` が既定 true のためコンテナが起動しない。結果として DataSource を生成できず、テストは必ず失敗する。

```kotlin
// TODO: Testcontainers を導入したら @Disabled を外す。
//  spring-boot-docker-compose は developmentOnly のためテストのクラスパスに載らず、
//  仮に載せても spring.docker.compose.skip.in-tests が既定 true なのでコンテナは起動しない。
//  そのため現状このテストは DataSource を生成できず必ず失敗する。
@Disabled("DataSource が必要。Testcontainers 導入後に有効化する")
@SpringBootTest
class TaskLabApiApplicationTests {
    // 以下略
}
```

---

## task-lab-core

### 8. タスク存在確認のドメインサービス作成

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/domain/task/domainService/TaskFoundDomainService.kt:12`
- TODO: `タスクが存在するのか確認するドメインサービスを作成する`（補足: `先にインターフェースを作成する必要がありそう。`）

クラス自体は実装済みだが、コメント 18〜20 行目に書かれた仕様のうち「2 件のタスクが見つかったらログを残してエラー型を返す」の分岐が未実装。

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
    fun execute(taskId: TaskId): Result<Task, FailureTaskNotFound> {
        // 以下略
    }
}
```

### 9. Kotlin-Result の使い方

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/usecase/task/RegisterTaskInteractor.kt:19`
- TODO: `Kotlin-Resultの使い方〜`

`runCatching` / `orElse` の組み合わせについての学習メモ。

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
}
```

### 10. sealed と object の使い分け

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/usecase/task/RegisterTaskInteractor.kt:32`
- TODO: `こういった場合にsealdなのかobjectを使用するのか判断できるようになりたい`

エラー型を `data object` 1 つで表現しているが、失敗理由が増えた場合に `sealed interface` へ変えるべきかという判断基準の学習メモ。

```kotlin
// TODO: こういった場合にsealdなのかobjectを使用するのか判断できるようになりたい
data object FailureRegisterTask
```

### 11. パッケージ構成の学習

- ファイル: `task-lab-core/src/main/kotlin/tasklab/core/usecase/task/UpdateTaskUseCase.kt:7`
- TODO: `パッケージ構成についても学ぶ必要がありそう。`

`UpdateTaskUseCase` インターフェースが実装クラス側のエラー型 `UpdateTaskInteractor.FailureUpdateTask` を参照しており、インターフェースが実装に依存する形になっている。

```kotlin
// TODO: パッケージ構成についても学ぶ必要がありそう。

interface UpdateTaskUseCase {

    fun execute(input: Input): Result<Output, UpdateTaskInteractor.FailureUpdateTask>
    // 以下略
}
```

---

## task-lab-infrastructure

### 12. 例外（非チェック例外）の扱い

- ファイル: `task-lab-infrastructure/src/main/kotlin/tasklab/infrastructure/persistence/TaskFoundJdbcAdapter.kt:11`
- TODO: `発生する例外に対する対処方法わかっているのか？（非チェック例外の対処方法など）`

`queryForObject` は結果が 0 件で `EmptyResultDataAccessException`、複数件で `IncorrectResultSizeDataAccessException` を投げる。これらは非チェック例外のためコンパイラが強制せず、アダプタ内では捕捉していない。

### 13. ラムダ式の理解

- ファイル: `task-lab-infrastructure/src/main/kotlin/tasklab/infrastructure/persistence/TaskFoundJdbcAdapter.kt:12`
- TODO: `ラムダ式について理解している？`

`queryForObject` に渡す `RowMapper` を trailing lambda で書いている箇所についての学習メモ。

```kotlin
// TODO: 発生する例外に対する対処方法わかっているのか？（非チェック例外の対処方法など）
// TODO: ラムダ式について理解している？

@Repository
class TaskFoundJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : TaskFoundRepositoryPort {

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

---

## プロジェクト全体

### 14. マイクロサービス化

- ファイル: `README.md:8`
- TODO: `このプロジェクトをマイクロサービス化する対応を行いたい`

現状は `task-lab-api` / `task-lab-core` / `task-lab-infrastructure` のマルチモジュール構成。

```markdown
TODO：このプロジェクトをマイクロサービス化する対応を行いたい
```
