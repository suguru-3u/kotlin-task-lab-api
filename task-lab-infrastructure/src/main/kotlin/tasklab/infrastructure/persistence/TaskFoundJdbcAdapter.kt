package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import tasklab.core.domain.task.Task
import tasklab.core.domain.task.TaskId
import tasklab.core.port.task.TaskFoundRepositoryPort
import kotlin.uuid.Uuid

// TODO: 発生する例外に対する対処方法わかっているのか？（非チェック例外の対処方法など）
// TODO: ラムダ式について理解している？

@Repository
class TaskFoundJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) : TaskFoundRepositoryPort {

    override fun execute(taskId: TaskId): Task {
        val sql = """"
            SELECT * FROM tasks WHERE task_id = ?
        """.trimIndent()

        val params = MapSqlParameterSource()
            .addValue("taskId", taskId.value.toBinary16())

        val paramSource = MapSqlParameterSource()

        return jdbcTemplate.queryForObject(sql, params) { rs, _ ->
            Task.fromRepository(
                id = taskId,
                title = rs.getString("title"),
                description = rs.getString("description")
            )
        }
    }

    /** kotlin.uuid.Uuid → MySQL BINARY(16)。java.util.UUID を経由しない */
    private fun Uuid.toBinary16(): ByteArray = toByteArray()
}
