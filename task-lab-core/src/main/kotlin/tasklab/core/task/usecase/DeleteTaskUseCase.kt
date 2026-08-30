package tasklab.core.task.usecase

import com.github.michaelbull.result.Result
import tasklab.core.task.domain.TaskId

interface DeleteTaskUseCase {

    fun execute(taskId: Input): Result<Unit, DeleteTaskInteractor.FailureDeleteTask>

    class Input(
        val taskId: TaskId
    )
}
