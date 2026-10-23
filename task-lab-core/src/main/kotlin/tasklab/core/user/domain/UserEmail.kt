package tasklab.core.user.domain

@JvmInline
value class UserEmail(
    val value: String
) {
    init {
        require(value.isNotEmpty()) { "User email must not be empty" }
        require(value.length <= 100) { "User email must not exceed 50" }
    }
}
