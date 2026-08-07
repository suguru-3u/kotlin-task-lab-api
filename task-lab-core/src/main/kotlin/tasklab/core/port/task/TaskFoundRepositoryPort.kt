package tasklab.core.port.task

import tasklab.core.domain.task.Task
import kotlin.uuid.Uuid

interface TaskFoundRepositoryPort {
    fun execute(taskId: Uuid): Task
}
