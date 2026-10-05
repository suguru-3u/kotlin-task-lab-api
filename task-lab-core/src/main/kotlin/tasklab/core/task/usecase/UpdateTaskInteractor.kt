package tasklab.core.task.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onErr
import com.github.michaelbull.result.onOk
import com.github.michaelbull.result.orElse
import com.github.michaelbull.result.runCatching
import jakarta.inject.Named
import tasklab.core.task.domainService.TaskFoundDomainService
import tasklab.core.task.port.TaskUpdateRepositoryPort

@Named
class UpdateTaskInteractor(
    private val taskFoundDomainService: TaskFoundDomainService,
    private val taskUpdateRepositoryPort: TaskUpdateRepositoryPort
) : UpdateTaskUseCase {
    override fun execute(input: UpdateTaskUseCase.Input): Result<UpdateTaskUseCase.Output, FailureUpdateTask> {
        return taskFoundDomainService.execute(input.task.id).mapError {
            FailureUpdateTask
        }.andThen { recordTask ->
            recordTask.update(input.task)
        }.mapError {
            FailureUpdateTask
        }.andThen { updateTask ->
            runCatching {
                taskUpdateRepositoryPort.execute(updateTask)
                UpdateTaskUseCase.Output(
                    taskId = updateTask.id.value,
                    title = updateTask.title.value,
                    description = updateTask.description.value,
                    status = updateTask.status.value.name,
                    dueDate = updateTask.dueDate.value,
                    priority = updateTask.priority.name
                )
            }.mapError {
                FailureUpdateTask
            }
        }
    }

    data object FailureUpdateTask
}
