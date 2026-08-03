package tasklab.core.usecase.task

import com.github.michaelbull.result.Result
import tasklab.core.domain.task.Task

interface RegisterTaskUseCase {
    fun execute(
        input: Input
    ): Result<Unit, Throwable>

    class Input(
        val task: Task,
    )
}
