package task_lab.backend.task

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tasklab.core.task.usecase.GetTasksUseCase

@RestController
@RequestMapping("/api/v1/tasks")
class GetTasksController(
    private val getTasksUseCase: GetTasksUseCase
) {
    @GetMapping
    fun execute(): List<GetTasksUseCase.Output> {
        return getTasksUseCase.execute()
    }
}
