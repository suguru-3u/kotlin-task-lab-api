package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import tasklab.core.task.domain.TaskId
import tasklab.core.task.port.DeleteTaskRepositoryPort

@Repository
class DeleteTaskJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : DeleteTaskRepositoryPort {

    override fun execute(taskId: TaskId) {
        val sql = """
            DELETE FROM tasks
            WHERE id = :id
        """.trimIndent()

        val params = MapSqlParameterSource()
            .addValue("id", taskId.value.toBinary16())

        val result = jdbcTemplate.update(sql, params)
        check(result == 1) { "Task deletion failed. result=$result" }
    }

    private fun kotlin.uuid.Uuid.toBinary16(): ByteArray = toByteArray()
}
