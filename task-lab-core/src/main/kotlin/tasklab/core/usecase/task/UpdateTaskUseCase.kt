package tasklab.core.usecase.task

import kotlin.uuid.Uuid

interface UpdateTaskUseCase {

    fun execute(input: Input): Output

    class Input(
        val taskId: Uuid,
        val title: String,
        val description: String,
    )

    class Output(
        val taskId: Uuid,
        val title: String,
        val description: String,
    )
}
