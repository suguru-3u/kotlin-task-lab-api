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

// TODO: なぜこの設定が必要なのか？　UTではなぜ不要なのか？
@SpringBootTest
@ApplyExtension(SpringExtension::class)
@Import(MySqlContainerConfig::class)
@AutoConfigureMockMvc
class TaskRegisterTest(
    val jdbcTemplate: JdbcTemplate,
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) : FreeSpec({
    // TODO:このthis asについて何をしているのか調べる
    this as TaskRegisterTest

    "タスクが登録できること" {

        val testDBTitle = "Test Task"

        val request = CreateTaskRequest(
            title = testDBTitle,
            description = "This is a test task."
        )

        val beforeCount = getDBTask(testDBTitle)
        val result = mockMvc.post("/api/v1/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
        val afterCount = getDBTask(testDBTitle)

        result.andReturn().response.status shouldBe HttpStatus.CREATED.value()
        afterCount shouldBe beforeCount + 1
    }
}) {
    private class CreateTaskRequest(
        val title: String,
        val description: String,
    )

    private fun getDBTask(title: String): Int {
        return jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM tasks WHERE title = ?",
            Int::class.java,
            title
        ) ?: 0
    }
}
