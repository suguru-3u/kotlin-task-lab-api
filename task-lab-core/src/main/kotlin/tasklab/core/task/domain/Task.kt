package tasklab.core.task.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import tasklab.core.task.domain.TaskStatus
import java.time.Instant
import java.time.LocalDateTime

class Task private constructor(
    val id: TaskId,
    val title: TaskTitle,
    val description: TaskDescription,
    val status: TaskStatus,
    val dueDate: TaskDueDate,
    val priority: TaskPriority,
) {
    companion object {
        fun fromCreateRequest(
            title: String,
            description: String
        ): Result<Task, Throwable> = runCatching {
            Task(
                id = TaskId.create(),
                title = TaskTitle(title),
                description = TaskDescription(description),
                status = TaskStatus.start(),
                dueDate = TaskDueDate(LocalDateTime.now()),
                priority = TaskPriority.NORMAL,
            )
        }

        fun fromUpdateRequest(
            id: String,
            title: String,
            description: String
        ): Result<Task, Throwable> = runCatching {
            Task(
                id = TaskId.fromString(id),
                title = TaskTitle(title),
                description = TaskDescription(description),
                status = TaskStatus.start(),
                dueDate = TaskDueDate(LocalDateTime.now()),
                priority = TaskPriority.NORMAL,
            )
        }

        fun fromRepository(
            id: String,
            title: String,
            description: String
        ): Task = Task(
            id = TaskId.fromString(id),
            title = TaskTitle(title),
            description = TaskDescription(description),
            status = TaskStatus.start(),
            dueDate = TaskDueDate(LocalDateTime.now()),
            priority = TaskPriority.NORMAL,
        )
    }
}
