package tasklab.core.task.port

import tasklab.core.task.domain.Task

interface TaskUpdateRepositoryPort {
    fun execute(task: Task)
}
