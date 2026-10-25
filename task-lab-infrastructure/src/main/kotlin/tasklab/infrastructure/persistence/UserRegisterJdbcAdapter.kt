package tasklab.infrastructure.persistence

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRegisterJdbcAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun execute() {
        val sql =
            """"
            INSERT INTO users (name, email, password)
            VALUES (:name, :email, :password)
            """.trimIndent()

        val params = MapSqlParameterSource()
            .addValue("name", "John Doe")
            .addValue("email", "sample@moja998877655.co.jp")
            .addValue("password", "sample")

        jdbcTemplate.update(sql, params)
    }
}
