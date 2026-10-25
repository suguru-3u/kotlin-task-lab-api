package tasklab.core.user.domain

@JvmInline
value class UserName(
    val value: String
) {
    init {
        require(value.isNotEmpty()) { "User name must not be empty" }
        require(value.length <= 50) { "User name must not exceed 50" }
    }
}
