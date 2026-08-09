package tasklab.core.usecase.task

import com.github.michaelbull.result.Result
import tasklab.core.domain.task.Task
import kotlin.uuid.Uuid

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
