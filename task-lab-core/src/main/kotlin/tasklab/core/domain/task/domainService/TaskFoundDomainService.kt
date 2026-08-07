package tasklab.core.domain.task.domainService

import jakarta.inject.Named
import tasklab.core.port.task.TaskFoundRepositoryPort

// TODO: タスクが存在するのか確認するドメインサービスを作成する
// 先にインターフェースを作成する必要がありそう。
@Named
class TaskFoundDomainService(
    private val taskFoundRepositoryPort: TaskFoundRepositoryPort
) {
    fun execute() {

    }
}
