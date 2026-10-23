package tasklab.core.user.domain

class RegisterUser(
    val name: UserName,
    val email: UserEmail,
    val password: RawPassword,
)
