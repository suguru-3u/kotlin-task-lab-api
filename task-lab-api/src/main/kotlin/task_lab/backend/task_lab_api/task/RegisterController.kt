package task_lab.backend.task_lab_api.task

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tasks")
internal class RegisterController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(@RequestBody request: Request) {
        print("RegisterController.execute() called")
        print("request: $request")
    }

    // TODO: バリデーションエラーが発生した際にのエラーレスポンスをカスタマイズする
    data class Request(
        val title: String,
        val description: String,
    )
}
