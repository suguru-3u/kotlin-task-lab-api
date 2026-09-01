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
import java.nio.ByteBuffer
import java.util.*

@SpringBootTest
@ApplyExtension(SpringExtension::class)
@Import(MySqlContainerConfig::class)
@AutoConfigureMockMvc
class DeleteTaskControllerTest(
    val jdbcTemplate: JdbcTemplate,
    val mockMvc: MockMvc
) : FreeSpec({
    this as DeleteTaskControllerTest

    beforeSpec {
        deleteTask()
        registerTask()
    }

    afterSpec {
        deleteTask()
    }

    "正常系" - {
        "タスクが削除できること" {
            val before = getDBTask("Task 1")
            // act
//            val result = mockMvc.get("/api/v1/tasks").andReturn()
            val after = getDBTask("Task 1")

            // verify
//            result.response.status shouldBe 204
            after shouldBe before - 1
        }
    }


}) {
    private fun registerTask() {
        val sql = "INSERT INTO tasks (id, title, description) VALUES (?, ?, ?)"
        jdbcTemplate.update(
            sql,
            uuidToBinary16("00000000-0000-7000-8000-000000000001"),
            "Task 1",
            "Description 1"
        )
    }

    private fun deleteTask() {
        jdbcTemplate.execute("DELETE FROM tasks")
    }

    private fun uuidToBinary16(value: String): ByteArray {
        val uuid = UUID.fromString(value)
        return ByteBuffer.allocate(16)
            .putLong(uuid.mostSignificantBits)
            .putLong(uuid.leastSignificantBits)
            .array()
    }

    private fun getDBTask(title: String): Int {
        return jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM tasks WHERE title = ?",
            Int::class.java,
            title
        ) ?: 0
    }
}
