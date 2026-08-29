package tasklab.core.task.usecase

interface GetTasksUseCase {

    fun execute(): List<Output>

    class Output(
        private val id: String,
        private val title: String,
        private val description: String,
    )
}
