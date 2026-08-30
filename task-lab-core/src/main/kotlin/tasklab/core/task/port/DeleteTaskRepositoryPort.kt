package tasklab.core.task.port

import tasklab.core.task.domain.TaskId

interface DeleteTaskRepositoryPort {

    fun execute(taskId: TaskId)
}
