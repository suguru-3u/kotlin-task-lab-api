package tasklab.core.task.usecase

import tasklab.core.task.port.GetTasksRepositoryPort
import tasklab.core.task.usecase.GetTasksUseCase.Output


class GetTasksInteractor(
    private val getTasksRepositoryPort: GetTasksRepositoryPort
) : GetTasksUseCase {

    override fun execute(): List<Output> {
        return getTasksRepositoryPort.execute().map {
            Output(
                id = it.id.value.toString(),
                title = it.title.value,
                description = it.description.value,
            )
        }
    }
}
