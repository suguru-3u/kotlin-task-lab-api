package task_lab.backend.task_lab_api.task

import com.github.michaelbull.result.Ok
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import tasklab.core.usecase.task.RegisterTaskUseCase
import tools.jackson.databind.ObjectMapper

class RegisterControllerTest() : FreeSpec({
    this as RegisterControllerTest

    "正常にタスクが登録できた場合" {
        // 1. モックの作成
        val registerTaskUseCase = mockk<RegisterTaskUseCase>()
        every { registerTaskUseCase.execute(any()) } returns Ok(Unit)

        val registerController = RegisterController(
            registerTaskUseCase = registerTaskUseCase
        )

        val mockMvc = MockMvcBuilders.standaloneSetup(registerController).build()

        val request = Request(
            title = "Test Task",
            description = "This is a test task."
        )

        // 関数の実行
        val result = mockMvc.post("/api/v1/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }

        result.andReturn().response.status shouldBe HttpStatus.CREATED.value()

        print("Test Execute! $result")
    }
}) {
    private class Request(
        val title: String,
        val description: String,
    )

    companion object {
        val objectMapper = ObjectMapper()
    }
}
