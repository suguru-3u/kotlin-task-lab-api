package tasklab.core.usecase.task

import tasklab.core.domain.task.Task
import kotlin.uuid.Uuid

interface UpdateTaskUseCase {

    fun execute(input: Input): Output

    class Input(
        val task: Task
    )

    class Output(
        val taskId: Uuid,
        val title: String,
        val description: String,
    )
}
