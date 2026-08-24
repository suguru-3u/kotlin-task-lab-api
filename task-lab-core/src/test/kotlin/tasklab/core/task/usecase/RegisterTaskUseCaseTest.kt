package tasklab.core.task.usecase

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.getOrThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import tasklab.core.task.domain.Task
import tasklab.core.task.port.TaskRegisterRepositoryPort

class RegisterTaskUseCaseTest : FreeSpec({

    "正常系" - {
        "ユースケースの実行が成功すること" {
            // ready
            val taskRegisterRepositoryPort = mockk<TaskRegisterRepositoryPort>()
            every { taskRegisterRepositoryPort.execute(any()) } returns Unit
            val registerTaskInteractor = RegisterTaskInteractor(taskRegisterRepositoryPort)

            // Act
            val result = registerTaskInteractor.execute(input = createTaskRequest())

            // Verify
            result.isOk shouldBe true
            result.isErr shouldBe false
        }
    }

    "異常系" - {
        "ユースケースの実行が失敗すること" {
            // ready
            val taskRegisterRepositoryPort = mockk<TaskRegisterRepositoryPort>()
            every { taskRegisterRepositoryPort.execute(any()) } throws Exception("登録に失敗しました")
            val registerTaskInteractor = RegisterTaskInteractor(taskRegisterRepositoryPort)

            // Act
            val result = registerTaskInteractor.execute(input = createTaskRequest())

            // Verify
            result.isOk shouldBe false
            result.isErr shouldBe true
            result shouldBe Err(RegisterTaskInteractor.FailureRegisterTask)
        }
    }
})

private fun createTaskRequest(): RegisterTaskUseCase.Input {
    return RegisterTaskUseCase.Input(
        task = Task.fromCreateRequest(
            title = "test title",
            description = "test description"
        ).getOrThrow()
    )
}

