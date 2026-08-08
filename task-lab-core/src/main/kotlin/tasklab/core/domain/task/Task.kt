package tasklab.core.domain.task

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching

class Task private constructor(
    val id: TaskId,
    val title: TaskTitle,
    val description: TaskDescription,
) {
    companion object {
        fun fromCreateRequest(title: String, description: String): Result<Task, Throwable> {
            return runCatching {
                Task(
                    id = TaskId.create(),
                    title = TaskTitle(title),
                    description = TaskDescription(description)
                )
            }
        }

        fun fromUpdateRequest(id: String, title: String, description: String): Result<Task, Throwable> {
            return runCatching {
                Task(
                    id = TaskId.fromString(id),
                    title = TaskTitle(title),
                    description = TaskDescription(description)
                )
            }
        }

        fun fromRepository(id: TaskId, title: String, description: String): Task {
            return Task(
                id = id,
                title = TaskTitle(title),
                description = TaskDescription(description)
            )
        }
    }
}
