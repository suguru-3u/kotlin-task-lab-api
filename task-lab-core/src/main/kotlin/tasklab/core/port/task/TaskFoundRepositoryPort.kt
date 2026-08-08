package tasklab.core.port.task

import tasklab.core.domain.task.Task
import tasklab.core.domain.task.TaskId

interface TaskFoundRepositoryPort {
    fun execute(taskId: TaskId): Task
}
