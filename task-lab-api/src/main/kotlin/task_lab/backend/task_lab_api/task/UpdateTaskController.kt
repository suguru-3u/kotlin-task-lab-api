package task_lab.backend.task_lab_api.task

import org.springframework.web.bind.annotation.*
import tasklab.core.usecase.task.UpdateTaskUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@RestController
@RequestMapping("/api/v1/tasks")
class UpdateTaskController(
    private val updateTaskUseCase: UpdateTaskUseCase
) {

    @OptIn(ExperimentalUuidApi::class)
    @PutMapping("/{taskId}")
    fun execute(@PathVariable taskId: Uuid, @RequestBody request: Request): Response {
        // TODO: IDの値オブジェクトを作成して、Inputクラスを作成する
        val input = UpdateTaskUseCase.Input(
            taskId = taskId,
            title = request.title,
            description = request.description
        )
        updateTaskUseCase.execute(input)

        return Response(
            taskId = taskId,
            title = request.title,
            description = request.description
        )


    }

    class Request(
        val title: String,
        val description: String,
    )


    // TODO: クラスや関数のスコープについて学習する
    class Response @OptIn(ExperimentalUuidApi::class) constructor(
        val taskId: Uuid,
        val title: String,
        val description: String,
    )
}
