package tasklab.core.task.domain

// taskのステータスを管理するクラス
// スタートは外部から作成できて完了にする場合、関数を通してじゃないと完了にすることができない

class TaskStatus private constructor(
    val value: Status
) {

    enum class Status {
        Start, Complete
    }

    companion object {
        fun start(): TaskStatus = TaskStatus(Status.Start)
    }

    fun complete(): TaskStatus = TaskStatus(Status.Complete)
}
