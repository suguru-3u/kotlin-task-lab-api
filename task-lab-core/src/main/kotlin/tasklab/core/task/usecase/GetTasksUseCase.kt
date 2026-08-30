package tasklab.core.task.usecase

interface GetTasksUseCase {

    fun execute(): List<Output>

    data class Output(
        val id: String,
        val title: String,
        val description: String,
    )
}
