package tasklab.core.task.port

import tasklab.core.task.domain.Task
import tasklab.core.task.domain.TaskStatus

interface GetTasksRepositoryPort {
    fun execute(input: Input): List<Task>

    class Input(
        val taskStatus: TaskStatus
    )
}
