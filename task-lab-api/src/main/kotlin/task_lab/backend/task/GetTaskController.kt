package task_lab.backend.task

import com.github.michaelbull.result.getOrThrow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tasklab.core.task.domain.TaskId
import tasklab.core.task.usecase.GetTaskUseCase

@RestController
@RequestMapping("/api/v1/tasks")
class GetTaskController(
    private val getTaskUseCase: GetTaskUseCase
) {

    @GetMapping("/{taskId}")
    fun execute(@PathVariable taskId: String): Response {
        val input = runCatching {
            GetTaskUseCase.Input(
                taskId = TaskId.fromString(taskId)
            )
        }.getOrElse {
            throw IllegalArgumentException("Invalid taskId format: $taskId")
        }

        val result = getTaskUseCase.execute(input = input).getOrThrow {
            throw IllegalArgumentException("Task not found for taskId: $taskId")
        }

        return Response(
            taskId = result.taskId,
            title = result.title,
            description = result.description
        )
    }

    class Response(
        val taskId: String,
        val title: String,
        val description: String,
    )
}
