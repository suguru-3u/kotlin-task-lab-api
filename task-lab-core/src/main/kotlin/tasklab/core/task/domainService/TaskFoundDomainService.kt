package tasklab.core.task.domainService

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.orElse
import com.github.michaelbull.result.runCatching
import jakarta.inject.Named
import tasklab.core.task.domain.Task
import tasklab.core.task.domain.TaskId
import tasklab.core.task.port.TaskFoundRepositoryPort

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
        return runCatching {
            taskFoundRepositoryPort.execute(taskId)
        }.orElse {
            print("タスクが見つかりませんでした。taskId: ${taskId.value}")
            print("error: ${it.message}")
            Err(FailureTaskNotFound)
        }
    }

    object FailureTaskNotFound
}
