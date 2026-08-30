package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import tasklab.core.task.domain.Task
import tasklab.core.task.port.GetTasksRepositoryPort

@Repository
class TaskGetJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : GetTasksRepositoryPort {
    // TODO: なぜBIN_TO_UUIDが必要になる？
    override fun execute(): List<Task> {
        val sql = """
            SELECT BIN_TO_UUID(id) AS id, title, description FROM tasks
        """.trimIndent()

        return jdbcTemplate.query(sql) { rs, _ ->
            Task.fromRepository(
                id = rs.getString("id"),
                title = rs.getString("title"),
                description = rs.getString("description"),
            )
        }
    }
}
