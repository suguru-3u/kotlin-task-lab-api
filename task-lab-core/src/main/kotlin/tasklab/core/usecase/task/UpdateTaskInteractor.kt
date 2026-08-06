package tasklab.core.usecase.task

class UpdateTaskInteractor : UpdateTaskUseCase {
    override fun execute(input: UpdateTaskUseCase.Input): UpdateTaskUseCase.Output {
        return UpdateTaskUseCase.Output(
            input.taskId,
            input.title,
            input.description,
        )
    }
}
