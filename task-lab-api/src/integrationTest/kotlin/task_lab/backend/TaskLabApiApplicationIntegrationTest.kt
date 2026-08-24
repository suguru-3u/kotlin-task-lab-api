package task_lab.backend

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class TaskLabApiApplicationIntegrationTest : IntegrationTestBase() {

    @Test
    fun `Spring コンテキストが起動する`() {
    }

    @Test
    fun `Flyway のマイグレーションが適用され tasks テーブルが存在する`() {
        val count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tasks", Int::class.java)
        count shouldBe 0
    }
}
