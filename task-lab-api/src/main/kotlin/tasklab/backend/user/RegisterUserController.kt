package tasklab.backend.user

import com.github.michaelbull.result.getOrElse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tasklab.core.user.domain.RawPassword
import tasklab.core.user.domain.UserEmail
import tasklab.core.user.domain.UserName
import tasklab.core.user.usecase.RegisterUserUseCase

@RestController
@RequestMapping("/api/v1/users")
class RegisterUserController(
    private val registerUserUseCase: RegisterUserUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(
        @RequestBody request: Request
    ) {
        // ユースケースを呼ぶ
        // ドメインクラスに変換する
        val input = RegisterUserUseCase.Input(
            registerUser = tasklab.core.user.domain.RegisterUser.fromRegisterRequest(
                name = request.name,
                email = request.email,
                password = request.password
            ).getOrElse { throw IllegalArgumentException("Invalid input: ${it.message}") }
        )
        registerUserUseCase.execute(input)
    }

    class Request(
        val name: String,
        val email: String,
        val password: String
    )
}
