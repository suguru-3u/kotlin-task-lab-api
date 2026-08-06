package task_lab.backend.task_lab_api.task

import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import task_lab.backend.task_lab_api.task.RegisterController.Request
import tasklab.core.usecase.task.UpdateTaskUseCase
import java.util.*

@RestController
@RequestMapping("/api/v1/tasks")
class UpdateTaskController(
    private val updateTaskUseCase: UpdateTaskUseCase
) {

    @PutMapping("/{taskId}")
    fun execute(@RequestBody request: Request) {
        val input = UpdateTaskUseCase.Input(
            taskId = 1L,
            title = request.title,
            description = request.description
        )
        updateTaskUseCase.execute(input)
    }

    data class Response(
        val taskId: UUID,
        val title: String,
        val description: String,
    )
}
