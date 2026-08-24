package tasklab.core.task.domain

@JvmInline
value class TaskDescription(val value: String) {
    init {
        require(value.trim().isNotEmpty()) { "Task description must not be empty" }
        require(value.length <= 500) { "Task description must not exceed 500" }
    }
}
