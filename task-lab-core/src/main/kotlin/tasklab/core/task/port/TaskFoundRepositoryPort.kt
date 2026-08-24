package tasklab.core.task.port

import tasklab.core.task.domain.Task
import tasklab.core.task.domain.TaskId

interface TaskFoundRepositoryPort {
    fun execute(taskId: TaskId): Task
}
