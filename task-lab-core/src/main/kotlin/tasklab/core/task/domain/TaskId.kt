package tasklab.core.task.domain

import kotlin.uuid.Uuid

@JvmInline
value class TaskId private constructor(
    val value: Uuid
) {
    companion object {
        fun create(): TaskId = TaskId(Uuid.generateV7())

        fun fromString(value: String): TaskId = TaskId(Uuid.parse(value))
    }
}
