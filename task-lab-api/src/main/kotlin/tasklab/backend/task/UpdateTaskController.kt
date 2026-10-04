package tasklab.backend.task

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.getOrThrow
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tasklab.core.task.domain.TaskChanges
import tasklab.core.task.usecase.UpdateTaskUseCase
import kotlin.uuid.ExperimentalUuidApi

@RestController
@RequestMapping("/api/v1/tasks")
class UpdateTaskController(
    private val updateTaskUseCase: UpdateTaskUseCase
) {
    @OptIn(ExperimentalUuidApi::class)
    @PatchMapping("/{taskId}")
    fun execute(
        @PathVariable taskId: String,
        @RequestBody request: Request
    ): Response {
        // TODO: 音声入力を使用できるようにしてもいいかも
        // TODO: IDの値オブジェクトを作成して、Inputクラスを作成する
        // TODO: リクエスト内容からデータを更新できるようにする,まずはfromUpdateRequestの改修
        val input = UpdateTaskUseCase.Input(
            task = TaskChanges
                .of(
                    id = taskId,
                    title = request.title,
                    description = request.description,
                    status = request.status,
                    dueDate = request.dueDate,
                    priority = request.priority
                ).getOrElse {
                    throw IllegalArgumentException("Invalid request")
                }
        )
        val result =
            updateTaskUseCase.execute(input).getOrThrow {
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
        val status: String,
        val dueDate: String,
        val priority: String
    )

    // TODO: クラスや関数のスコープについて学習する
    class Response(
        val taskId: String,
        val title: String,
        val description: String
    )
}
