package tasklab.core.task.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onErr
import jakarta.inject.Named
import tasklab.core.task.domainService.TaskFoundDomainService
import tasklab.core.task.port.DeleteTaskRepositoryPort

@Named
class DeleteTaskInteractor(
    private val taskFoundDomainService: TaskFoundDomainService,
    private val deleteTaskRepositoryPort: DeleteTaskRepositoryPort
) : DeleteTaskUseCase {
    override fun execute(taskId: DeleteTaskUseCase.Input): Result<Unit, FailureDeleteTask> {
        // TODO:ここの存在チェックの処理は必要か検討する
        taskFoundDomainService.execute(taskId.taskId).onErr {
            return Err(FailureDeleteTask)
        }
        deleteTaskRepositoryPort.execute(taskId.taskId)

        return Ok(Unit)
    }

    data object FailureDeleteTask
}
