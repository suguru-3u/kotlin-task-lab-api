package tasklab.core.usecase.task

import com.github.michaelbull.result.*
import tasklab.core.domain.task.domainService.TaskFoundDomainService
import tasklab.core.port.task.TaskUpdateRepositoryPort

class UpdateTaskInteractor(
    private val taskFoundDomainService: TaskFoundDomainService,
    private val taskUpdateRepositoryPort: TaskUpdateRepositoryPort
) : UpdateTaskUseCase {
    override fun execute(input: UpdateTaskUseCase.Input): Result<UpdateTaskUseCase.Output, FailureUpdateTask> {

        taskFoundDomainService.execute(input.task.id).onErr {
            return Err(FailureUpdateTask)
        }

        return runCatching {
            taskUpdateRepositoryPort.execute(input.task)
            UpdateTaskUseCase.Output(
                input.task.id.value,
                input.task.title.value,
                input.task.description.value,
            )

        }.orElse {
            Err(FailureUpdateTask)
        }
    }

    data object FailureUpdateTask
}
