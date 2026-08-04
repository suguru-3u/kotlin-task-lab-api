package tasklab.core.usecase.task

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.orElse
import com.github.michaelbull.result.runCatching
import jakarta.inject.Named
import tasklab.core.port.task.TaskRegisterRepositoryPort


/**
 * JSR-330 の @Named。Spring は jakarta.inject.Named を @Component と同等のステレオタイプとして
 * コンポーネントスキャンで拾うため、core が Spring に依存しないまま DI コンテナに載る。
 *
 * - @Inject は不要（コンストラクタが1つだけのクラスは暗黙的にコンストラクタ注入される）
 * - スコープは singleton。JSR-330 仕様の既定は prototype だが、Spring は自身の既定に合わせて
 *   @Named Bean を singleton として登録する。Interactor はステートレスなのでこれでよい。
 */
// TODO: Kotlin-Resultの使い方〜
@Named
class RegisterTaskInteractor(
    private val taskRegisterRepositoryPort: TaskRegisterRepositoryPort,
) : RegisterTaskUseCase {
    override fun execute(input: RegisterTaskUseCase.Input): Result<Unit, FailureRegisterTask> {
        return runCatching {
            taskRegisterRepositoryPort.save(task = input.task)
        }.orElse {
            Err(FailureRegisterTask)
        }
    }

    // TODO: こういった場合にsealdなのかobjectを使用するのか判断できるようになりたい
    data object FailureRegisterTask
}
