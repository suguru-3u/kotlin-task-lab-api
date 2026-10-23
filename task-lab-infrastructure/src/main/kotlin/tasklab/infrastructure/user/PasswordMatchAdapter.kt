package tasklab.infrastructure.user

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import tasklab.core.user.domain.RawPassword
import tasklab.core.user.domain.UserEmail
import tasklab.core.user.port.PasswordMatch

@Repository
class PasswordMatchAdapter(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val passwordEncoder: PasswordEncoder
) : PasswordMatch {

    override fun execute(userEmail: UserEmail, rawPassword: RawPassword): Boolean {
        // ユーザーのパスワードを取得する
        // 検証する
        val sql = """
            SELECT password
            FROM users
            WHERE email = :email
        """.trimIndent()

        val params = MapSqlParameterSource()
            .addValue("email", userEmail.value)

        val encodePassword = jdbcTemplate.queryForObject(sql, params, String::class.java)

        return passwordEncoder.matches(rawPassword.value, encodePassword)
    }
}
