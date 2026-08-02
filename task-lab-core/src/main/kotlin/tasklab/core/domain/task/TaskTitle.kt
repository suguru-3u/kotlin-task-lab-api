package tasklab.core.domain.task

@JvmInline
value class TaskTitle(
    val value: String
) {
    init {
        require(value.isNotEmpty()) { "Task title must not be empty" }
        require(value.length <= 50) { "Task title must not exceed 50" }
    }
}
