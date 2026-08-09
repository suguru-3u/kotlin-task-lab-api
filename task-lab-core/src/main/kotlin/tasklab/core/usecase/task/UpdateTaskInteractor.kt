package tasklab.core.usecase.task

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onErr
import tasklab.core.domain.task.domainService.TaskFoundDomainService

class UpdateTaskInteractor(
    private val taskFoundDomainService: TaskFoundDomainService
) : UpdateTaskUseCase {
    override fun execute(input: UpdateTaskUseCase.Input): Result<UpdateTaskUseCase.Output, FailureUpdateTask> {

        taskFoundDomainService.execute(input.task.id).onErr {
            return Err(FailureUpdateTask)
        }

        return Ok(
            UpdateTaskUseCase.Output(
                input.task.id.value,
                input.task.title.value,
                input.task.description.value,
            )
        )
    }

    data object FailureUpdateTask
}
