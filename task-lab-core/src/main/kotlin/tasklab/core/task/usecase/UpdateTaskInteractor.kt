package tasklab.core.task.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
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
        val recordTask = taskFoundDomainService.execute(input.task.id).onErr {
            return Err(FailureUpdateTask)
        }

        val updateTask = recordTask.onOk { it.update(input.task) }.getOrElse {
            return Err(FailureUpdateTask)
        }

        return runCatching {
            taskUpdateRepositoryPort.execute(updateTask)
            UpdateTaskUseCase.Output(
                taskId = updateTask.id.value,
                title = updateTask.title.value,
                description = updateTask.description.value,
                status = updateTask.status.value.name,
                dueDate = updateTask.dueDate.value.toString(),
                priority = updateTask.priority.name
            )
        }.orElse {
            Err(FailureUpdateTask)
        }
    }

    data object FailureUpdateTask
}
