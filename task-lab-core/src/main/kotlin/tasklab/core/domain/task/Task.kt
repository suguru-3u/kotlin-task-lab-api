package tasklab.core.domain.task

import kotlin.uuid.Uuid

class Task private constructor(
    val id: Uuid,
    val title: TaskTitle,
    val description: TaskDescription,
) {
    companion object {
        fun create(title: String, description: String): Task {
            return Task(
                id = Uuid.generateV7(),
                title = TaskTitle(title),
                description = TaskDescription(description)
            )
        }
    }
}
