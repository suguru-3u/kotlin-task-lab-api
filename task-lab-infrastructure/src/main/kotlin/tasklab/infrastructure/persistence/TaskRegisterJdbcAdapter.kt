package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tasklab.core.domain.task.Task
import tasklab.core.port.task.TaskRegisterRepositoryPort
import kotlin.uuid.Uuid

@Repository
internal class TaskRegisterJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : TaskRegisterRepositoryPort {

    @Transactional
    override fun execute(task: Task) {
        val sql = """
            INSERT INTO tasks (id, title, description)
            VALUES (:id, :title, :description)
        """.trimIndent()

        val params = MapSqlParameterSource()
            .addValue("id", task.id.value.toBinary16())
            .addValue("title", task.title.value)
            .addValue("description", task.description.value)

        val resultRows = jdbcTemplate.update(sql, params)
        check(resultRows != 1) { "Task insert failed. resultRows=$resultRows" }
    }

    /** kotlin.uuid.Uuid → MySQL BINARY(16)。java.util.UUID を経由しない */
    private fun Uuid.toBinary16(): ByteArray = toByteArray()
}
