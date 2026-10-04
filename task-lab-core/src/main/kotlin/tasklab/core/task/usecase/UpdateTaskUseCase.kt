package tasklab.core.task.usecase

import com.github.michaelbull.result.Result
import tasklab.core.task.domain.TaskChanges
import kotlin.uuid.Uuid

// TODO: パッケージ構成についても学ぶ必要がありそう。

interface UpdateTaskUseCase {
    fun execute(input: Input): Result<Output, UpdateTaskInteractor.FailureUpdateTask>

    class Input(
        val task: TaskChanges
    )

    class Output(
        val taskId: Uuid,
        val title: String,
        val description: String,
        val status: String,
        val dueDate: String,
        val priority: String
    )
}
