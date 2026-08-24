package tasklab.core.task.usecase

import com.github.michaelbull.result.Result
import tasklab.core.task.domain.Task

interface RegisterTaskUseCase {
    fun execute(
        input: Input
    ): Result<Unit, RegisterTaskInteractor.FailureRegisterTask>

    class Input(
        val task: Task,
    )
}
