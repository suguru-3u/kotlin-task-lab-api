package tasklab.backend.task

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.runCatching
import org.apache.coyote.BadRequestException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tasklab.core.task.domain.TaskStatus
import tasklab.core.task.usecase.GetTasksUseCase

@RestController
@RequestMapping("/api/v1/tasks")
class GetTasksController(
    private val getTasksUseCase: GetTasksUseCase
) {
    // 受け取れるようにするのは、まずはステータス
    @GetMapping
    fun execute(@RequestParam status: String): List<GetTasksUseCase.Output> {
        val taskStatus = runCatching {
            TaskStatus.of(status)
        }.getOrElse {
            throw BadRequestException("Invalid request value: $status")
        }

        val input = GetTasksUseCase.Input(
            taskStatus = taskStatus
        )

        return getTasksUseCase.execute(input)
    }
}
