package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import tasklab.core.port.task.TaskUpdateRepositoryPort

@Repository
class TaskUpdateJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : TaskUpdateRepositoryPort {
    override fun execute(task: tasklab.core.domain.task.Task) {

        val sql = """
            UPDATE tasks
            SET title = :title, description = :description
            WHERE id = :id
        """.trimIndent()

        val params = MapSqlParameterSource()
            .addValue("id", task.id.value.toBinary16())
            .addValue("title", task.title.value)
            .addValue("description", task.description.value)

        val resultRow = jdbcTemplate.update(sql, params)
        check(resultRow == 1) { "Task update failed. resultRow=$resultRow" }
    }

    private fun kotlin.uuid.Uuid.toBinary16(): ByteArray = toByteArray()
}
