package task_lab.backend.task_lab_api

import io.kotest.core.spec.style.FreeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
class TaskRegisterTest(
    val jdbcTemplate: JdbcTemplate,
    val mockMvc: MockMvc,
) : FreeSpec({

    "タスクが登録できること" {
        val testDBTitle = "Test Task"
        val beforeTasks = jdbcTemplate.execute("SELECT * FROM tasks WHERE title")
        val result = mockMvc.post("/tasks") {
            accept = MediaType.APPLICATION_JSON
            param("title", "Test Task")
            param("description", "This is a test task.")
        }

        result.andExpect {
            status { isCreated() }
        }

        val afterTasks = jdbcTemplate.execute("SELECT * FROM tasks")


    }
    // テスト用にDBの中身を確認する


    // 実際に登録処理を行う
    // DBにデータが登録されていることを確認する
})
