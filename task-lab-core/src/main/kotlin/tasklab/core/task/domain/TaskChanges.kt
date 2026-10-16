package tasklab.core.task.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import java.time.LocalDate

class TaskChanges private constructor(
    val id: TaskId,
    val title: TaskTitle?,
    val description: TaskDescription?,
    val status: TaskStatus?,
    val dueDate: TaskDueDate?,
    val priority: TaskPriority?
) {
    companion object {
        fun of(
            id: String,
            title: String?,
            description: String?,
            status: String?,
            dueDate: LocalDate?,
            priority: String?
        ): Result<TaskChanges, Throwable> = runCatching {
            TaskChanges(
                id = TaskId.fromString(id),
                title = title?.let { TaskTitle(it) },
                description = description?.let { TaskDescription(it) },
                status = status?.let { TaskStatus.of(it) },
                dueDate = dueDate?.let { TaskDueDate(dueDate) },
                priority = priority?.let { TaskPriority.valueOf(it) }
            )
        }
    }
}
