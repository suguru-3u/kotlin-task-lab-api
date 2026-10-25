package tasklab.core.user.usecase

import tasklab.core.user.domain.RegisterUser

interface RegisterUserUseCase {

    fun execute(input: Input)

    class Input(
        val registerUser: RegisterUser
    )
}
