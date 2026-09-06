package tasklab.core.task.usecase

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import jakarta.inject.Named
import tasklab.core.task.domainService.TaskFoundDomainService

@Named
class GetTaskInteractor(
    private val taskFoundDomainService: TaskFoundDomainService
) : GetTaskUseCase {
    override fun execute(input: GetTaskUseCase.Input): Result<GetTaskUseCase.Output, FailureGetTask> {
        return taskFoundDomainService.execute(input.taskId)
            .mapError { FailureGetTask }
            .map {
                GetTaskUseCase.Output(
                    taskId = input.taskId.value.toString(),
                    title = it.title.value,
                    description = it.description.value
                )
            }
    }

    data object FailureGetTask
}
