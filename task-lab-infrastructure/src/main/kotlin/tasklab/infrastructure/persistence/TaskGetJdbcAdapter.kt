package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import tasklab.core.task.domain.Task
import tasklab.core.task.port.GetTasksRepositoryPort
import tasklab.core.task.port.GetTasksRepositoryPort.Input

@Repository
class TaskGetJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : GetTasksRepositoryPort {
    // TODO: なぜBIN_TO_UUIDが必要になる？
    override fun execute(input: Input): List<Task> {
        val sql =
            """
            SELECT BIN_TO_UUID(id) AS id, title, description FROM tasks WHERE status = ?
            """.trimIndent()

        val params = MapSqlParameterSource().addValue("status", input.taskStatus.value)

        return jdbcTemplate.query(sql, params) { rs, _ ->
            Task.fromRepository(
                id = rs.getString("id"),
                title = rs.getString("title"),
                description = rs.getString("description")
            )
        }
    }
}
