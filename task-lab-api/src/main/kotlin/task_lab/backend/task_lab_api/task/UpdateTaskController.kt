package task_lab.backend.task_lab_api.task

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.getOrThrow
import org.springframework.web.bind.annotation.*
import tasklab.core.domain.task.Task
import tasklab.core.usecase.task.UpdateTaskUseCase
import kotlin.uuid.ExperimentalUuidApi

@RestController
@RequestMapping("/api/v1/tasks")
class UpdateTaskController(
    private val updateTaskUseCase: UpdateTaskUseCase
) {
    // TODO:スタイルガイドの導入を検討する

    @OptIn(ExperimentalUuidApi::class)
    @PutMapping("/{taskId}")
    fun execute(@PathVariable taskId: String, @RequestBody request: Request): Response {
        // TODO: 音声入力を使用できるようにしてもいいかも
        // TODO: IDの値オブジェクトを作成して、Inputクラスを作成する
        val input = UpdateTaskUseCase.Input(
            task = Task.fromUpdateRequest(
                id = taskId,
                title = request.title,
                description = request.description
            ).getOrElse {
                throw IllegalArgumentException("Invalid request")
            }
        )
        val result = updateTaskUseCase.execute(input).getOrThrow {
            throw IllegalArgumentException("Invalid request")
        }

        return Response(
            taskId = result.taskId.toString(),
            title = result.title,
            description = result.description
        )


    }

    class Request(
        val title: String,
        val description: String,
    )


    // TODO: クラスや関数のスコープについて学習する
    class Response(
        val taskId: String,
        val title: String,
        val description: String,
    )
}
