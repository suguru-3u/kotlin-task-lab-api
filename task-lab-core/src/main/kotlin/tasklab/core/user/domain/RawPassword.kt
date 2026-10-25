package tasklab.core.user.domain

@JvmInline
value class RawPassword(
    val value: String
) {
    init {
        require(value.isNotEmpty()) { "Password must not be empty" }
    }
}
