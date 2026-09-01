package task_lab.backend.task

import com.github.michaelbull.result.getOrThrow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import tasklab.core.task.domain.TaskId
import tasklab.core.task.usecase.DeleteTaskUseCase

@RestController
@RequestMapping("/api/v1/tasks")
class DeleteTaskController(
    private val deleteTaskUseCase: DeleteTaskUseCase
) {

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun execute(@PathVariable taskId: String) {
        val taskId = TaskId.fromString(taskId)

        deleteTaskUseCase.execute(
            taskId = DeleteTaskUseCase.Input(taskId = taskId)
        ).getOrThrow {
            throw IllegalArgumentException("Invalid request")
        }
    }
}
