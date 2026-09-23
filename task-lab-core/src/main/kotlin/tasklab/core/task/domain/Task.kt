package tasklab.core.task.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import tasklab.core.task.domain.TaskStatus

class Task private constructor(
    val id: TaskId,
    val title: TaskTitle,
    val description: TaskDescription,
    val status: TaskStatus
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
                status = TaskStatus.start()
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
                status = TaskStatus.start()
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
            status = TaskStatus.start()
        )
    }
}
