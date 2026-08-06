package tasklab.core.usecase.task

import com.github.michaelbull.result.Result

interface UpdateTaskUseCase {

    fun execute(input: Input): Result<Unit, Throwable>

    class Input(
        val taskId: Long,
        val title: String,
        val description: String,
    )
}
