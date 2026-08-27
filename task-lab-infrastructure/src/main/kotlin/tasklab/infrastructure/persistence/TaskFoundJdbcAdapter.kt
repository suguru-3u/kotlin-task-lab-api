package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tasklab.core.task.domain.Task
import tasklab.core.task.domain.TaskId
import tasklab.core.task.port.TaskFoundRepositoryPort
import kotlin.uuid.Uuid

// TODO: 発生する例外に対する対処方法わかっているのか？（非チェック例外の対処方法など）
// TODO: ラムダ式について理解している？

@Repository
class TaskFoundJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : TaskFoundRepositoryPort {

    @Transactional
    override fun execute(taskId: TaskId): Task {
        val sql = """
            SELECT * FROM tasks WHERE id = :taskId
        """.trimIndent()

        val params = MapSqlParameterSource()
            .addValue("taskId", taskId.value.toBinary16())

        return jdbcTemplate.queryForObject(sql, params) { rs, _ ->
            Task.fromRepository(
                id = rs.getString("id"),
                title = rs.getString("title"),
                description = rs.getString("description")
            )
        }
    }

    /** kotlin.uuid.Uuid → MySQL BINARY(16)。java.util.UUID を経由しない */
    private fun Uuid.toBinary16(): ByteArray = toByteArray()
}
