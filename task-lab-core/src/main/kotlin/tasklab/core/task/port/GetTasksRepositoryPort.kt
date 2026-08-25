package tasklab.core.task.port

import tasklab.core.task.domain.Task

interface GetTasksRepositoryPort {
    fun execute(): List<Task>
}
