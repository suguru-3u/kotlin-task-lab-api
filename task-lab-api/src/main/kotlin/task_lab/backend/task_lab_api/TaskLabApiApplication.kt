package task_lab.backend.task_lab_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskLabApiApplication

fun main(args: Array<String>) {
	runApplication<TaskLabApiApplication>(*args)
}
