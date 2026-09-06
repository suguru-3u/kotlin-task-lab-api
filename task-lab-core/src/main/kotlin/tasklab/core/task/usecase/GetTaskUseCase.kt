package tasklab.core.task.usecase

import com.github.michaelbull.result.Result
import tasklab.core.task.domain.TaskId

interface GetTaskUseCase {
    fun execute(input: Input): Result<Output, GetTaskInteractor.FailureGetTask>

    class Input(
        val taskId: TaskId
    )

    class Output(
        val taskId: String,
        val title: String,
        val description: String,
    )
}
