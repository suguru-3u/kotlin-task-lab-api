package tasklab.infrastructure.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import tasklab.core.user.domain.HashedPassword
import tasklab.core.user.domain.RawPassword
import tasklab.core.user.port.PasswordHasher

@Repository
class PasswordHasherAdapter(
    private val passwordEncoder: PasswordEncoder
) : PasswordHasher {

    override fun execute(rawPassword: RawPassword): HashedPassword {
        val hashPassword =
            passwordEncoder.encode(rawPassword.value) ?: throw IllegalStateException("Password hashing failed")
        return HashedPassword(hashPassword)
    }
}
