package task_lab.backend.task_lab_api.task

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import tasklab.core.domain.task.Task
import tasklab.core.usecase.task.RegisterTaskUseCase

@RestController
@RequestMapping("/api/v1/tasks")
class RegisterController(
    val registerTaskUseCase: RegisterTaskUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(@RequestBody request: Request) {
        val request = Task.create(
            title = request.title,
            description = request.description
        ).getOrElse {
            throw IllegalArgumentException("Invalid request")
        }

        // TODO: ここの例外処理を見直す。controllerのadviceのクラスを作成する
        // このプロジェクトではKotlin Resultを採用した方が良さそう。
        try {
            val task = RegisterTaskUseCase.Input(
                task = request
            )
            registerTaskUseCase.execute(input = task)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid request: ${e.message}")
        }


    }

    // TODO: バリデーションエラーが発生した際にのエラーレスポンスをカスタマイズする
    data class Request(
        val title: String,
        val description: String,
    )
}
