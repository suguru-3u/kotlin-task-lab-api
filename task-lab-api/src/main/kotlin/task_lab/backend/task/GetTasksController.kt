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

    // ユースケースの作成
    @GetMapping
    fun execute(): List<Task> {
        getTasksUseCase.execute()
        return listOf(
            Task(
                id = "1",
                title = "Task 1",
                description = "Description for Task 1"
            ),
            Task(
                id = "2",
                title = "Task 2",
                description = "Description for Task 2"
            )
        )
    }

    class Task(
        val id: String,
        val title: String,
        val description: String,
    )
}
