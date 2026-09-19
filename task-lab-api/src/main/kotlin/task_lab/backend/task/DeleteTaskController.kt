package task_lab.backend.task

import com.github.michaelbull.result.getOrThrow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tasklab.core.task.domain.TaskId
import tasklab.core.task.usecase.DeleteTaskUseCase

@RestController
@RequestMapping("/api/v1/tasks")
class DeleteTaskController(
    private val deleteTaskUseCase: DeleteTaskUseCase,
) {
    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun execute(
        @PathVariable taskId: String,
    ) {
        val taskId = TaskId.fromString(taskId)

        deleteTaskUseCase
            .execute(
                taskId = DeleteTaskUseCase.Input(taskId = taskId),
            ).getOrThrow {
                throw IllegalArgumentException("Invalid request")
            }
    }
}
