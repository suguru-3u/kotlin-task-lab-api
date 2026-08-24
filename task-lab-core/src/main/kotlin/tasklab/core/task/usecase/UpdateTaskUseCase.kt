package tasklab.core.task.usecase

import com.github.michaelbull.result.Result
import tasklab.core.task.domain.Task
import kotlin.uuid.Uuid

// TODO: パッケージ構成についても学ぶ必要がありそう。

interface UpdateTaskUseCase {

    fun execute(input: Input): Result<Output, UpdateTaskInteractor.FailureUpdateTask>

    class Input(
        val task: Task
    )

    class Output(
        val taskId: Uuid,
        val title: String,
        val description: String,
    )
}
