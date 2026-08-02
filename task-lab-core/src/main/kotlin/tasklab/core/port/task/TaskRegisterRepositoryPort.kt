package tasklab.core.port.task

import tasklab.core.domain.task.Task

interface TaskRegisterRepositoryPort {
    fun save(task: Task)
}
