package tasklab.infrastructure.persistence

import tasklab.core.task.domain.Task
import tasklab.core.task.domain.TaskId
import tasklab.core.task.port.GetTasksRepositoryPort

class TaskGetJdbcAdapter : GetTasksRepositoryPort {
    override fun execute(): List<Task> {
        // DBから情報を取得する処理を実装する
        return listOf(
            Task.fromRepository(
                id = TaskId.fromString("1"),
                title = "Sample Task",
                description = "This is a sample task.",
            )
        )
    }
}
