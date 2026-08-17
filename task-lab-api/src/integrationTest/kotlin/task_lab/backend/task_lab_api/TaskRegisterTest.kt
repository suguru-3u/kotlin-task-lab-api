package task_lab.backend.task_lab_api

import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import tools.jackson.databind.ObjectMapper

// TODO: テストコードの公式サイト、技術選定に関してmdファイルにまとめる
// https://kotest.io/docs/framework/project-setup.html

@SpringBootTest
@ApplyExtension(SpringExtension::class)
@Import(MySqlContainerConfig::class)
@AutoConfigureMockMvc
class TaskRegisterTest(
    val jdbcTemplate: JdbcTemplate,
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) : FreeSpec({

    "タスクが登録できること" {

        val request = CreateTaskRequest(
            title = "Test Task",
            description = "This is a test task."
        )
        val testDBTitle = "Test Task"

        val beforeCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM tasks WHERE title = ?",
            Int::class.java,
            testDBTitle
        ) ?: 0
        val result = mockMvc.post("/api/v1/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
        val afterCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM tasks WHERE title = ?",
            Int::class.java,
            testDBTitle
        ) ?: 0

        result.andReturn().response.status shouldBe HttpStatus.CREATED.value()
        afterCount shouldBe beforeCount + 1
    }
}) {
    data class CreateTaskRequest(
        val title: String,
        val description: String,
    )
}
