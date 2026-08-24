package task_lab.backend.task_lab_api.task

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.getOrThrow
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import tasklab.core.task.domain.Task
import tasklab.core.task.usecase.RegisterTaskUseCase

@RestController
@RequestMapping("/api/v1/tasks")
class RegisterController(
    val registerTaskUseCase: RegisterTaskUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(@RequestBody request: Request) {
        // TODO: ここの例外処理を見直す。controllerのadviceのクラスを作成する
        // TODO: ログにloggerを使用するようにしてもいいかもしれない
        val task = RegisterTaskUseCase.Input(
            task = Task.fromCreateRequest(
                title = request.title,
                description = request.description
            ).getOrElse {
                throw BadRequestException("Invalid request")
            }
        )
        registerTaskUseCase.execute(input = task).getOrThrow {
            throw IllegalArgumentException("Invalid request")
        }
    }

    // TODO: バリデーションエラーが発生した際にのエラーレスポンスをカスタマイズする
    class Request(
        val title: String,
        val description: String,
    )
}
