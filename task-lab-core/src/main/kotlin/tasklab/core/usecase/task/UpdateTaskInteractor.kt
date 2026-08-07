package tasklab.core.usecase.task

import tasklab.core.domain.task.domainService.TaskFoundDomainService

class UpdateTaskInteractor(
    private val taskFoundDomainService: TaskFoundDomainService
) : UpdateTaskUseCase {
    override fun execute(input: UpdateTaskUseCase.Input): UpdateTaskUseCase.Output {
        return UpdateTaskUseCase.Output(
            input.task.id.value,
            input.task.title.value,
            input.task.description.value,
        )
    }
}
