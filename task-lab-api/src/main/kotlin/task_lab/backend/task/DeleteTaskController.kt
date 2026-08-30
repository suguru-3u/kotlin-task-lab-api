package task_lab.backend.task

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tasks")
class DeleteTaskController {

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun execute(@PathVariable taskId: String) {
        // ユースケースの呼び出しを行う
    }
}
