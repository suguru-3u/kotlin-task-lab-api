package tasklab.core.user.port

import tasklab.core.user.domain.HashedPassword
import tasklab.core.user.domain.RawPassword

interface PasswordHasher {
    fun execute(rawPassword: RawPassword): HashedPassword
}
