package tasklab.core.task.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withTests
import io.kotest.matchers.shouldBe

class TaskTitleTest : FreeSpec({

    "正常系" - {
        withTests(
            listOf(
                ValidTitle("1文字の場合", "A"),
                ValidTitle("50文字の場合", "A".repeat(50)),
            ),
        ) { (_, input) ->
            TaskTitle(input).value shouldBe input
        }
    }

    "異常系" - {
        withTests(
            listOf(
                InvalidTitle("空文字の場合", "", "Task title must not be empty"),
                InvalidTitle("境界値の51文字の場合", "A".repeat(51), "Task title must not exceed 50"),
            ),
        ) { (_, input, message) ->
            shouldThrow<IllegalArgumentException> {
                TaskTitle(input)
            }.message shouldBe message
        }
    }
})

private data class ValidTitle(
    val label: String,
    val input: String,
)

private data class InvalidTitle(
    val label: String,
    val input: String,
    val message: String,
)
