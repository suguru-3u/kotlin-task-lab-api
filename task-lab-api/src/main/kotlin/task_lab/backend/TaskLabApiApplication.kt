package task_lab.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "task_lab.backend",        // 自モジュールの Controller などを拾う
        "tasklab.core",            // JSR-330 の @Named が付いた Interactor などを拾う
        "tasklab.infrastructure",  // runtimeOnly なので文字列でしか指せない
    ]
)
class TaskLabApiApplication

fun main(args: Array<String>) {
    runApplication<TaskLabApiApplication>(*args)
}
