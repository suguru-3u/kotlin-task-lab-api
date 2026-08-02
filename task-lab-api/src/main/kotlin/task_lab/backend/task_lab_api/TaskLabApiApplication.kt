package task_lab.backend.task_lab_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "task_lab.backend.task_lab_api",   // 自モジュール（明示しないと既定スキャンが消える）
        "tasklab.core.usecase",            // JSR-330 の @Named が付いた Interactor を拾う
        "tasklab.infrastructure",          // runtimeOnly なので文字列でしか指せない
    ]
)
class TaskLabApiApplication

fun main(args: Array<String>) {
    runApplication<TaskLabApiApplication>(*args)
}
