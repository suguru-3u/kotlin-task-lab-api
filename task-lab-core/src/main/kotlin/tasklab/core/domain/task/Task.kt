package tasklab.core.domain.task

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import kotlin.uuid.Uuid

class Task private constructor(
    val id: Uuid,
    val title: TaskTitle,
    val description: TaskDescription,
) {
    companion object {
        fun create(title: String, description: String): Result<Task, Throwable> {
            return runCatching {
                Task(
                    id = Uuid.generateV7(),
                    title = TaskTitle(title),
                    description = TaskDescription(description)
                )
            }
        }
    }
}
