package tasklab.core.port.task

import tasklab.core.domain.task.Task

interface TaskUpdateRepositoryPort {
    fun execute(task: Task)
}
