package tasklab.core.task.usecase

import tasklab.core.task.domain.TaskStatus

interface GetTasksUseCase {
    fun execute(input: Input): List<Output>

    data class Output(
        val id: String,
        val title: String,
        val description: String
    )

    class Input(
        val taskStatus: TaskStatus
    )
}
