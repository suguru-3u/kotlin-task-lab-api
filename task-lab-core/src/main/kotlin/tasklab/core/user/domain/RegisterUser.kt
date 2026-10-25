package tasklab.core.user.domain

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.runCatching

class RegisterUser(
    val name: UserName,
    val email: UserEmail,
    val password: RawPassword,
) {
    companion object {
        fun fromRegisterRequest(
            name: String,
            email: String,
            password: String
        ): Result<RegisterUser, Throwable> {
            return runCatching {
                RegisterUser(
                    name = UserName(name),
                    email = UserEmail(email),
                    password = RawPassword(password)
                )
            }
        }
    }
}
