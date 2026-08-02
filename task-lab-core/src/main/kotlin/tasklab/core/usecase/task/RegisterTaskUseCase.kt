package tasklab.core.usecase.task

import tasklab.core.domain.task.Task

interface RegisterTaskUseCase {
    fun execute(
        input: Input
    )

    class Input(
        val task: Task,
    )
}
