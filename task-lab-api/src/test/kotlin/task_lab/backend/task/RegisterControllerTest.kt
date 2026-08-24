package task_lab.backend.task

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import tasklab.core.task.usecase.RegisterTaskInteractor
import tasklab.core.task.usecase.RegisterTaskUseCase
import tools.jackson.databind.ObjectMapper

class RegisterControllerTest() : FreeSpec({
    this as RegisterControllerTest

    "正常系" - {
        "正常にタスクが登録できた場合" {
            // ready
            val mockMvc = buildSuccessMockMvc()
            val request = successRequest()

            // act
            val result = requestMockApi(mockMvc, request)

            // verify
            result.andReturn().response.status shouldBe HttpStatus.CREATED.value()
        }
    }

    "異常系" - {
        "タスクを登録が失敗した場合" {
            // ready
            val mockMvc = buildFailMockMvc()
            val request = successRequest()

            // act
            val result = requestMockApi(mockMvc, request)

            // verify
            result.andReturn().response.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
        }

        "タスク登録のリクエスト内容が不正な値だった場合" {
            // ready
            val mockMvc = buildFailMockMvc()
            val request = failRequest()

            // act
            val result = requestMockApi(mockMvc, request)

            // verify
            result.andReturn().response.status shouldBe HttpStatus.BAD_REQUEST.value()
        }
    }

}) {
    class Request(
        val title: String,
        val description: String,
    )

    companion object {
        val objectMapper = ObjectMapper()
    }

    private fun successRequest(): Request {
        return Request(
            title = "Test Task",
            description = "This is a test task."
        )
    }

    private fun failRequest(): Request {
        return Request(
            title = "Test Task aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            description = "This is a test task."
        )
    }

    private fun buildSuccessMockMvc(): MockMvc {
        val registerTaskUseCase = mockk<RegisterTaskUseCase>()
        every { registerTaskUseCase.execute(any()) } returns Ok(Unit)

        val registerController = RegisterController(
            registerTaskUseCase = registerTaskUseCase
        )

        return MockMvcBuilders.standaloneSetup(registerController).build()
    }

    private fun buildFailMockMvc(): MockMvc {
        val registerTaskUseCase = mockk<RegisterTaskUseCase>()
        every { registerTaskUseCase.execute(any()) } returns Err(RegisterTaskInteractor.FailureRegisterTask)

        val registerController = RegisterController(
            registerTaskUseCase = registerTaskUseCase
        )

        return MockMvcBuilders.standaloneSetup(registerController).setControllerAdvice(TaskExceptionHandler()).build()
    }


    private fun requestMockApi(mockMvc: MockMvc, request: Request): ResultActionsDsl {
        return mockMvc.post("/api/v1/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
    }
}
