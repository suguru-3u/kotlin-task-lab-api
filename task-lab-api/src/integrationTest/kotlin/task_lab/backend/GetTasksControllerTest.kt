package task_lab.backend

import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.nio.ByteBuffer
import java.util.*

@SpringBootTest
@ApplyExtension(SpringExtension::class)
@Import(MySqlContainerConfig::class)
@AutoConfigureMockMvc
class GetTasksControllerTest(
    val jdbcTemplate: JdbcTemplate,
    val mockMvc: MockMvc
) : FreeSpec({
    this as GetTasksControllerTest

    beforeSpec {
        deleteTasks()
        insertTasks()
    }

    afterSpec {
        deleteTasks()
    }

    "正常系" - {
        "タスク一覧が取得できる場合" {
            // act
            val result = mockMvc.get("/api/v1/tasks").andReturn()

            // verify
            result.response.status shouldBe 200
            result.response.contentAsString.contains("Task 1") shouldBe true
            result.response.contentAsString.contains("Task 2") shouldBe true
        }
    }
}) {
    private fun deleteTasks() {
        jdbcTemplate.execute("DELETE FROM tasks")
    }

    private fun insertTasks() {
        val sql = "INSERT INTO tasks (id, title, description) VALUES (?, ?, ?)"
        val batchArgs: List<Array<Any>> = listOf(
            arrayOf(
                uuidToBinary16("00000000-0000-7000-8000-000000000001"),
                "Task 1",
                "Description 1"
            ),
            arrayOf(
                uuidToBinary16("00000000-0000-7000-8000-000000000002"),
                "Task 2",
                "Description 2"
            ),
        )

        jdbcTemplate.batchUpdate(sql, batchArgs)
    }

    private fun uuidToBinary16(value: String): ByteArray {
        val uuid = UUID.fromString(value)
        return ByteBuffer.allocate(16)
            .putLong(uuid.mostSignificantBits)
            .putLong(uuid.leastSignificantBits)
            .array()
    }
}
