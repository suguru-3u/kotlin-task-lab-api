package tasklab.core.task.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onErr
import jakarta.inject.Named
import tasklab.core.task.domainService.TaskFoundDomainService

@Named
class DeleteTaskInteractor(
    private val taskFoundDomainService: TaskFoundDomainService,
) : DeleteTaskUseCase {
    override fun execute(taskId: DeleteTaskUseCase.Input): Result<Unit, FailureDeleteTask> {
        taskFoundDomainService.execute(taskId.taskId).onErr {
            return Err(FailureDeleteTask)
        }

        return Ok(Unit)
    }

    data object FailureDeleteTask
}
