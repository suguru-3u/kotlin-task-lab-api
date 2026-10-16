package tasklab.core.task.usecase

import jakarta.inject.Named
import tasklab.core.task.port.GetTasksRepositoryPort
import tasklab.core.task.usecase.GetTasksUseCase.Output

@Named
class GetTasksInteractor(
    private val getTasksRepositoryPort: GetTasksRepositoryPort
) : GetTasksUseCase {
    override fun execute(input: GetTasksUseCase.Input): List<Output> {
        val input = GetTasksRepositoryPort.Input(input.taskStatus)
        return getTasksRepositoryPort.execute(input).map {
            Output(
                id = it.id.value.toString(),
                title = it.title.value,
                description = it.description.value
            )
        }
    }
}
