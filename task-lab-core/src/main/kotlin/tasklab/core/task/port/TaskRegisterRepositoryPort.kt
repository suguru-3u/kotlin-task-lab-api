package tasklab.core.task.port

import tasklab.core.task.domain.Task

interface TaskRegisterRepositoryPort {
    fun execute(task: Task)
}
