package tasklab.core.user.port

import tasklab.core.user.domain.RawPassword
import tasklab.core.user.domain.UserEmail

interface PasswordMatch {
    fun execute(userEmail: UserEmail, rawPassword: RawPassword): Boolean
}
